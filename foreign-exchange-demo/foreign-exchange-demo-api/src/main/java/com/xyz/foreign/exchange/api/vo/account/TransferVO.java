package com.xyz.foreign.exchange.api.vo.account;

import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 转账数据
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
@Data
public class TransferVO implements Serializable {

    private static final long serialVersionUID = 988429090894627343L;
    /**
     * 交易方用户id
     */
    private long fromUid;

    /**
     * 交易对象用户id
     */
    private long toUid;

    /**
     * 交易币种
     */
    private CurrencyEnum currency;

    /**
     * 金额 单位：分
     */
    private long amount;

    /**
     * 交易id
     */
    private long exchangeId;

}
