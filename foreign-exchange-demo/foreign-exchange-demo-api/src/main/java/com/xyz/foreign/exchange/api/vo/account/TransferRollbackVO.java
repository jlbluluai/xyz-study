package com.xyz.foreign.exchange.api.vo.account;

import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhu WeiJie
 * @date 2021/8/21
 **/
@Data
public class TransferRollbackVO implements Serializable {

    private static final long serialVersionUID = 7646826010144547262L;

    /**
     * 交易方用户id
     */
    private long fromUid;

    /**
     * 交易对象用户id
     */
    private long toUid;

    /**
     * 兑币种
     */
    private CurrencyEnum fromCurrency;

    /**
     * 换币种
     */
    private CurrencyEnum toCurrency;

    /**
     * 交易id
     */
    private long exchangeId;
}
