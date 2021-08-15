package com.xyz.study.hmily.demo.api.vo;

import lombok.Data;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
@Data
public class OrderAddVO {
    private long productId;
    private int stockAmount;
}
