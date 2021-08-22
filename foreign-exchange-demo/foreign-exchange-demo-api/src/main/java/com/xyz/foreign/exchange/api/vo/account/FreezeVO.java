package com.xyz.foreign.exchange.api.vo.account;

import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@Data
public class FreezeVO implements Serializable {

    private static final long serialVersionUID = 8421250266336078415L;
    /**
     * 冻结账户的用户id
     */
    private long uid;

    /**
     * 冻结账户的币种类型
     */
    private CurrencyEnum currency;

    /**
     * 冻结金额 单位：分
     */
    private long amount;

    /**
     * 交易id
     */
    private long exchangeId;
}
