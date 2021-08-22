package com.xyz.foreign.exchange.account.domain;

import lombok.Data;

/**
 * 用户人民币账户表
 */
@Data
public class AccountRmb {
    /**
     * 账户id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 余额
     */
    private Long balance;

    /**
     * 添加时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;
}