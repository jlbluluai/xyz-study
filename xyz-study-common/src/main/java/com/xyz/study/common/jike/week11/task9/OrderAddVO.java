package com.xyz.study.common.jike.week11.task9;

import lombok.Data;

/**
 * @author Zhu WeiJie
 * @date 2021/9/2
 **/
@Data
public class OrderAddVO {

    /**
     * 产品id
     */
    private long productId;

    /**
     * 数量
     */
    private int amount;
}
