package com.xyz.foreign.exchange.account.service;

import com.xyz.foreign.exchange.api.AccountInterface;
import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import com.xyz.foreign.exchange.api.vo.account.FreezeVO;
import com.xyz.foreign.exchange.api.vo.account.TransferRollbackVO;
import com.xyz.foreign.exchange.api.vo.account.TransferVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一的账户服务（暴露出去的服务）
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@DubboService(version = "1.0.0", interfaceClass = AccountInterface.class, timeout = 5000)
public class AccountService implements AccountInterface, InitializingBean {

    @Resource
    private List<AbstractAccountService> accountServiceList;

    private Map<CurrencyEnum, AbstractAccountService> accountServiceMap;

    @Override
    public void transfer(TransferVO paramVO) {
        accountServiceMap.get(paramVO.getCurrency()).transfer(paramVO);
    }

    @Override
    public void freeze(FreezeVO paramVO) {
        accountServiceMap.get(paramVO.getCurrency()).freeze(paramVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void tryRollbackTransfer(TransferRollbackVO paramVO) {
        accountServiceMap.get(paramVO.getFromCurrency()).tryRollbackTransfer(paramVO);
        accountServiceMap.get(paramVO.getToCurrency()).tryRollbackTransfer(paramVO);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        accountServiceMap = accountServiceList.stream()
                .collect(Collectors.toMap(AbstractAccountService::getCurrencyEnum, e -> e));
    }
}
