package com.xyz.study.common.jike.week7.task2;

import lombok.Data;

/**
 * xyz
 *
 * @author Zhu WeiJie
 * @date 2021/8/5
 **/
@Data
public class Order {

    private long id;

    private long uid;

    private long productId;

    private String productSnapshot;

    private int productAmount;

    private int orderStatus;

    private long orderPrice;

    private long createTime;

    private long updateTime;

}
