package com.xyz.foreign.exchange.account.service;

import com.xyz.foreign.exchange.account.dao.AccountDollarMapper;
import com.xyz.foreign.exchange.account.dao.FreezeDollarMapper;
import com.xyz.foreign.exchange.account.domain.AccountDollar;
import com.xyz.foreign.exchange.account.domain.FreezeDollar;
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
 * 美元相关账户服务
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@Service
@Slf4j
public class AccountDollarService extends AbstractAccountService {

    @Resource
    private AccountDollarMapper accountDollarMapper;

    @Resource
    private FreezeDollarMapper freezeDollarMapper;

    @Override
    public CurrencyEnum getCurrencyEnum() {
        return CurrencyEnum.DOLLAR;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transfer(TransferVO paramVO) {
        // 查询双方账户信息并锁住
        List<AccountDollar> accountDollars = accountDollarMapper.selectByUidsForUpdate(Arrays.asList(paramVO.getFromUid(), paramVO.getToUid()));
        if (accountDollars.size() < 2) {
            throw new BusinessException("请确保转账双方均开通");
        }

        //
        Map<Long, AccountDollar> collect = accountDollars.stream().collect(Collectors.toMap(AccountDollar::getUid, e -> e));
        AccountDollar from = collect.get(paramVO.getFromUid());
        AccountDollar to = collect.get(paramVO.getToUid());

        // 扣减from 增加to
        if (from.getBalance() < paramVO.getAmount()) {
            throw new BusinessException("账户余额不足");
        }
        long now = System.currentTimeMillis();
        accountDollarMapper.updateBalance(from.getId(), -paramVO.getAmount(), now);
        accountDollarMapper.updateBalance(to.getId(), paramVO.getAmount(), now);

        // 完成A的资金冻结
        freezeDollarMapper.updateStatus(paramVO.getFromUid(), paramVO.getExchangeId(), 1, now);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void freeze(FreezeVO paramVO) {
        // 查询账户信息并锁住
        AccountDollar accountDollar = accountDollarMapper.selectByUidForUpdate(paramVO.getUid());
        if (null == accountDollar) {
            throw new BusinessException("账户信息不存在");
        }

        // 锁住资金
        if (accountDollar.getBalance() < paramVO.getAmount()) {
            throw new BusinessException("账户资金不够");
        }

        FreezeDollar freezeDollar = new FreezeDollar();
        freezeDollar.setUid(paramVO.getUid());
        freezeDollar.setAmount(paramVO.getAmount());
        freezeDollar.setExchangeId(paramVO.getExchangeId());
        long now = System.currentTimeMillis();
        freezeDollar.setStatus(0);
        freezeDollar.setCreateTime(now);
        freezeDollar.setUpdateTime(now);
        freezeDollarMapper.insert(freezeDollar);
    }

    @Override
    public void tryRollbackTransfer(TransferRollbackVO paramVO) {
        log.info("尝试回滚美元转账 param = {}", paramVO);
        // 尝试查出完成的，若有，说明需要回滚
        FreezeDollar freezeDollar;
        long uid;
        long toUid;
        if (paramVO.getFromCurrency() == CurrencyEnum.DOLLAR) {
            uid = paramVO.getFromUid();
            toUid = paramVO.getToUid();
            freezeDollar = freezeDollarMapper.selectByStatus(paramVO.getFromUid(), paramVO.getExchangeId(), 1);
        } else {
            uid = paramVO.getToUid();
            toUid = paramVO.getFromUid();
            freezeDollar = freezeDollarMapper.selectByStatus(paramVO.getToUid(), paramVO.getExchangeId(), 1);
        }
        if (null == freezeDollar) {
            log.info("尝试回滚美元转账，无需回滚 param = {}", paramVO);
            return;
        }

        // 反过来转账
        freezeDollarMapper.updateStatus(uid, paramVO.getExchangeId(), 0, System.currentTimeMillis());

        // 查询双方账户信息并锁住
        List<AccountDollar> accountDollars = accountDollarMapper.selectByUidsForUpdate(Arrays.asList(uid, toUid));
        if (accountDollars.size() < 2) {
            throw new BusinessException("请确保转账双方均开通");
        }

        //
        Map<Long, AccountDollar> collect = accountDollars.stream().collect(Collectors.toMap(AccountDollar::getUid, e -> e));
        AccountDollar from = collect.get(uid);
        AccountDollar to = collect.get(toUid);

        long now = System.currentTimeMillis();
        accountDollarMapper.updateBalance(from.getId(), freezeDollar.getAmount(), now);
        accountDollarMapper.updateBalance(to.getId(), -freezeDollar.getAmount(), now);
    }

}
