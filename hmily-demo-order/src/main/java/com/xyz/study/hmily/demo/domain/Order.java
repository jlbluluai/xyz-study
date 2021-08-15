package com.xyz.study.hmily.demo.domain;

import lombok.Data;

/**
    * 订单表
    */
@Data
public class Order {
    /**
    * PK
    */
    private Long id;

    /**
    * 用户id
    */
    private Long uid;

    /**
    * 商品id
    */
    private Long productId;

    /**
    * 商品数量
    */
    private Integer productAmount;

    /**
    * 订单状态 0:待支付 1:待发货 2:待收货 3:已完成 4:已取消
    */
    private Byte orderStatus;

    /**
    * 订单金额 单位:分
    */
    private Long orderPrice;

    /**
    * 添加时间
    */
    private Long createTime;

    /**
    * 修改时间
    */
    private Long updateTime;
}