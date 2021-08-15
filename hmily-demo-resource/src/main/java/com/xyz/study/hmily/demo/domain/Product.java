package com.xyz.study.hmily.demo.domain;

import lombok.Data;

/**
    * 商品表
    */
@Data
public class Product {
    /**
    * PK
    */
    private Long id;

    /**
    * 商品名
    */
    private String productName;

    /**
    * 商品类型
    */
    private Byte productType;

    /**
    * 商品单价 单位:分
    */
    private Long productPrice;

    /**
    * 商品状态 0:创建 1:上架 2:下架
    */
    private Integer productStatus;

    /**
    * 商品库存
    */
    private Integer stockAmount;

    /**
    * 添加时间
    */
    private Long createTime;

    /**
    * 修改时间
    */
    private Long updateTime;
}