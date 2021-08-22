package com.xyz.foreign.exchange.account.service;

import com.xyz.foreign.exchange.account.dao.AccountRmbMapper;
import com.xyz.foreign.exchange.account.dao.FreezeRmbMapper;
import com.xyz.foreign.exchange.account.domain.AccountRmb;
import com.xyz.foreign.exchange.account.domain.FreezeRmb;
import com.xyz.foreign.exchange.account.exception.BusinessException;
import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import com.xyz.foreign.exchange.api.vo.account.FreezeVO;
import com.xyz.foreign.exchange.api.vo.account.TransferRollbackVO;
import com.xyz.foreign.exchange.api.vo.account.TransferVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人民币账号相关服务
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@Service
@Slf4j
public class AccountRmbService extends AbstractAccountService {

    @Resource
    private AccountRmbMapper accountRmbMapper;

    @Resource
    private FreezeRmbMapper freezeRmbMapper;

    @Override
    public CurrencyEnum getCurrencyEnum() {
        return CurrencyEnum.RMB;
    }

    @Override
    public void tryDelFreeze(long uid, long exchangeId) {
        freezeRmbMapper.del(uid, exchangeId, System.currentTimeMillis());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transfer(TransferVO paramVO) {
        // 查询双方账户信息并锁住
        List<AccountRmb> accountRmbs = accountRmbMapper.selectByUidsForUpdate(Arrays.asList(paramVO.getFromUid(), paramVO.getToUid()));
        if (accountRmbs.size() < 2) {
            throw new BusinessException("请确保转账双方均开通");
        }

        //
        Map<Long, AccountRmb> collect = accountRmbs.stream().collect(Collectors.toMap(AccountRmb::getUid, e -> e));
        AccountRmb from = collect.get(paramVO.getFromUid());
        AccountRmb to = collect.get(paramVO.getToUid());

        // 扣减from 增加to
        if (from.getBalance() < paramVO.getAmount()) {
            throw new BusinessException("账户余额不足");
        }
        long now = System.currentTimeMillis();
        accountRmbMapper.updateBalance(from.getId(), -paramVO.getAmount(), now);
        accountRmbMapper.updateBalance(to.getId(), paramVO.getAmount(), now);

        // 完成A的资金冻结
        freezeRmbMapper.updateStatus(paramVO.getFromUid(), paramVO.getExchangeId(), 1, now);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void freeze(FreezeVO paramVO) {
        // 查询账户信息并锁住
        AccountRmb accountRmb = accountRmbMapper.selectByUidForUpdate(paramVO.getUid());
        if (null == accountRmb) {
            throw new BusinessException("账户信息不存在");
        }

        // 锁住资金
        if (accountRmb.getBalance() < paramVO.getAmount()) {
            throw new BusinessException("账户资金不够");
        }

        FreezeRmb freezeRmb = new FreezeRmb();
        freezeRmb.setUid(paramVO.getUid());
        freezeRmb.setAmount(paramVO.getAmount());
        freezeRmb.setExchangeId(paramVO.getExchangeId());
        long now = System.currentTimeMillis();
        freezeRmb.setStatus(0);
        freezeRmb.setCreateTime(now);
        freezeRmb.setUpdateTime(now);
        freezeRmbMapper.insert(freezeRmb);
    }

    @Override
    public void tryRollbackTransfer(TransferRollbackVO paramVO) {
        log.info("尝试回滚人民币转账 param = {}", paramVO);
        // 尝试查出完成的，若有，说明需要回滚
        FreezeRmb freezeRmb;
        long uid;
        long toUid;
        if (paramVO.getFromCurrency() == CurrencyEnum.RMB) {
            uid = paramVO.getFromUid();
            toUid = paramVO.getToUid();
            freezeRmb = freezeRmbMapper.selectByStatus(paramVO.getFromUid(), paramVO.getExchangeId(), 1);
        } else {
            uid = paramVO.getToUid();
            toUid = paramVO.getFromUid();
            freezeRmb = freezeRmbMapper.selectByStatus(paramVO.getToUid(), paramVO.getExchangeId(), 1);
        }
        if (null == freezeRmb) {
            log.info("尝试回滚人民币转账，无需回滚 param = {}", paramVO);
            return;
        }

        // 反过来转账
        freezeRmbMapper.updateStatus(uid, paramVO.getExchangeId(), 0, System.currentTimeMillis());

        // 查询双方账户信息并锁住
        List<AccountRmb> accountRmbs = accountRmbMapper.selectByUidsForUpdate(Arrays.asList(uid, toUid));
        if (accountRmbs.size() < 2) {
            throw new BusinessException("请确保转账双方均开通");
        }

        //
        Map<Long, AccountRmb> collect = accountRmbs.stream().collect(Collectors.toMap(AccountRmb::getUid, e -> e));
        AccountRmb from = collect.get(uid);
        AccountRmb to = collect.get(toUid);

        long now = System.currentTimeMillis();
        accountRmbMapper.updateBalance(from.getId(), freezeRmb.getAmount(), now);
        accountRmbMapper.updateBalance(to.getId(), -freezeRmb.getAmount(), now);
    }

}
