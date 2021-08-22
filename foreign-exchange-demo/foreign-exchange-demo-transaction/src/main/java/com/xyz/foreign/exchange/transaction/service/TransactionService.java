package com.xyz.foreign.exchange.transaction.service;

import com.xyz.foreign.exchange.api.AccountInterface;
import com.xyz.foreign.exchange.api.TransactionInterface;
import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import com.xyz.foreign.exchange.api.vo.account.FreezeVO;
import com.xyz.foreign.exchange.api.vo.account.TransferRollbackVO;
import com.xyz.foreign.exchange.api.vo.account.TransferVO;
import com.xyz.foreign.exchange.api.vo.tarnsaction.AgreeTransactionVO;
import com.xyz.foreign.exchange.api.vo.tarnsaction.InitiateTransactionVO;
import com.xyz.foreign.exchange.transaction.dao.ForeignExchangeMapper;
import com.xyz.foreign.exchange.transaction.domain.ForeignExchange;
import com.xyz.foreign.exchange.transaction.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@Slf4j
@DubboService(version = "1.0.0")
public class TransactionService implements TransactionInterface {

    @DubboReference(version = "1.0.0", interfaceClass = AccountInterface.class)
    private AccountInterface accountInterface;

    @Resource
    private ForeignExchangeMapper foreignExchangeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initiateTransaction(InitiateTransactionVO paramVO) {
        // prepare
        CurrencyEnum fromCurrency = CurrencyEnum.resolve(paramVO.getFromCurrencyCode());
        if (null == fromCurrency) {
            throw new BusinessException("出币种有误");
        }
        CurrencyEnum toCurrency = CurrencyEnum.resolve(paramVO.getToCurrencyCode());
        if (null == toCurrency) {
            throw new BusinessException("入币种有误");
        }

        // 创建交易单子
        ForeignExchange foreignExchange = new ForeignExchange();
        foreignExchange.setFromUid(paramVO.getUid());
        foreignExchange.setFromOutCurrency(fromCurrency.getCode());
        foreignExchange.setFromOutAmount(paramVO.getAmount());
        foreignExchange.setFromInCurrency(toCurrency.getCode());
        // 跟汇率相关的等对手方同意交易时取当前汇率计算
        foreignExchange.setFromInAmount(0L);
        foreignExchange.setExchangeRate("");
        foreignExchange.setToUid(paramVO.getToUid());
        foreignExchange.setExchangeStatus(0);
        long now = System.currentTimeMillis();
        foreignExchange.setCreateTime(now);
        foreignExchange.setUpdateTime(now);
        foreignExchangeMapper.insert(foreignExchange);

        // 冻结用户对应币种账户金额
        FreezeVO freezeVO = new FreezeVO();
        freezeVO.setUid(paramVO.getUid());
        freezeVO.setCurrency(fromCurrency);
        freezeVO.setAmount(paramVO.getAmount());
        freezeVO.setExchangeId(foreignExchange.getId());
        accountInterface.freeze(freezeVO);
    }

    @HmilyTCC(confirmMethod = "agreeTransactionConfirm", cancelMethod = "agreeTransactionCancel")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agreeTransaction(AgreeTransactionVO paramVO) {
        // 查询交易单子信息
        ForeignExchange foreignExchange = foreignExchangeMapper.selectByPrimaryKey(paramVO.getExchangeId());
        if (null == foreignExchange) {
            throw new BusinessException("交易不存在");
        }

        // prepare
        CurrencyEnum fromCurrency = CurrencyEnum.resolve(foreignExchange.getFromOutCurrency());
        CurrencyEnum toCurrency = CurrencyEnum.resolve(foreignExchange.getFromInCurrency());

        // 计算汇算后的金额
        long inAmount = BigDecimal.valueOf(foreignExchange.getFromOutAmount()).multiply(
                BigDecimal.valueOf(fromCurrency.getRate())
                        .divide(BigDecimal.valueOf(toCurrency.getRate()), 8, RoundingMode.HALF_DOWN)
        ).setScale(0, RoundingMode.HALF_DOWN).longValue();

        // 冻结用户对应币种账户金额
        FreezeVO freezeVO = new FreezeVO();
        freezeVO.setUid(paramVO.getUid());
        freezeVO.setCurrency(toCurrency);
        freezeVO.setAmount(inAmount);
        freezeVO.setExchangeId(foreignExchange.getId());
        accountInterface.freeze(freezeVO);

        // 完成用户A的转账操作
        TransferVO transferVOA = new TransferVO();
        transferVOA.setFromUid(foreignExchange.getFromUid());
        transferVOA.setToUid(foreignExchange.getToUid());
        transferVOA.setCurrency(fromCurrency);
        transferVOA.setAmount(foreignExchange.getFromOutAmount());
        transferVOA.setExchangeId(foreignExchange.getId());
        accountInterface.transfer(transferVOA);

        // 完成用户B的转账操作
        TransferVO transferVOB = new TransferVO();
        transferVOB.setFromUid(foreignExchange.getToUid());
        transferVOB.setToUid(foreignExchange.getFromUid());
        transferVOB.setCurrency(toCurrency);
        transferVOB.setAmount(inAmount);
        transferVOB.setExchangeId(foreignExchange.getId());
        accountInterface.transfer(transferVOB);

        // 修改交易单
        foreignExchange.setFromInAmount(inAmount);
        foreignExchange.setExchangeRate(fromCurrency.getRate() + ":" + toCurrency.getRate());
        foreignExchange.setExchangeStatus(1);
        foreignExchange.setUpdateTime(System.currentTimeMillis());
        foreignExchangeMapper.updateByPrimaryKey(foreignExchange);
    }

    public void agreeTransactionConfirm(AgreeTransactionVO paramVO) {
        log.info("agreeTransaction confirm");
    }

    public void agreeTransactionCancel(AgreeTransactionVO paramVO) {
        log.info("agreeTransaction cancel param = {}", paramVO);
        // 查询交易单子信息
        ForeignExchange foreignExchange = foreignExchangeMapper.selectByPrimaryKey(paramVO.getExchangeId());
        if (null == foreignExchange) {
            throw new BusinessException("交易不存在");
        }

        // 尝试回滚交易双方的交易
        TransferRollbackVO rollbackVO = new TransferRollbackVO();
        rollbackVO.setFromUid(foreignExchange.getFromUid());
        rollbackVO.setToUid(foreignExchange.getToUid());
        rollbackVO.setFromCurrency(CurrencyEnum.resolve(foreignExchange.getFromOutCurrency()));
        rollbackVO.setToCurrency(CurrencyEnum.resolve(foreignExchange.getFromInCurrency()));
        rollbackVO.setExchangeId(foreignExchange.getId());
        accountInterface.tryRollbackTransfer(rollbackVO);
    }

}
