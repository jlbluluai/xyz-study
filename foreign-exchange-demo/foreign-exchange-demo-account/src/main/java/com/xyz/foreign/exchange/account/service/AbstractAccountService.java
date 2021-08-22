package com.xyz.foreign.exchange.account.service;

import com.xyz.foreign.exchange.api.AccountInterface;
import com.xyz.foreign.exchange.api.enums.CurrencyEnum;

/**
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
public abstract class AbstractAccountService implements AccountInterface {

    /**
     * 设置账户服务币种
     */
    public abstract CurrencyEnum getCurrencyEnum();

    /**
     * 尝试删除冻结
     */
    public abstract void tryDelFreeze(long uid,long exchangeId);


}
