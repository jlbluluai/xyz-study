package com.xyz.foreign.exchange.api.vo.tarnsaction;

import lombok.Data;

/**
 * @author Zhu WeiJie
 * @date 2021/8/19
 **/
@Data
public class InitiateTransactionVO {

    /**
     * 用户id
     */
    private long uid;
    /**
     * 交易对象id
     */
    private long toUid;
    /**
     * 出币种编号
     */
    private int fromCurrencyCode;
    /**
     * 入币种编号
     */
    private int toCurrencyCode;
    /**
     * 金额 单位：分
     */
    private long amount;


}
