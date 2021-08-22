package com.xyz.foreign.exchange.account.domain;

import lombok.Data;

/**
 * 用户人民币冻结表
 */
@Data
public class FreezeRmb {
    /**
     * PK
     */
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 金额
     */
    private Long amount;

    /**
     * 交易id
     */
    private Long exchangeId;

    private Integer status;

    /**
     * 添加时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;
}