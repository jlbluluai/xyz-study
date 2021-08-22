package com.xyz.foreign.exchange.transaction.domain;

import lombok.Data;

/**
 * 外汇交易表
 */
@Data
public class ForeignExchange {
    /**
     * PK
     */
    private Long id;

    /**
     * 发起交易用户id
     */
    private Long fromUid;

    /**
     * 发起出币种
     */
    private Integer fromOutCurrency;

    /**
     * 发起出金额
     */
    private Long fromOutAmount;

    /**
     * 发起进币种
     */
    private Integer fromInCurrency;

    /**
     * 发起进金额
     */
    private Long fromInAmount;

    /**
     * 交易汇率 只做该笔交易时汇率展示用 按发起者出币种 : 入币种
     */
    private String exchangeRate;

    /**
     * 交易对象用户id
     */
    private Long toUid;

    /**
     * 交易状态 0:进行中 1:完成 2:取消
     */
    private Integer exchangeStatus;

    /**
     * 添加时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;
}