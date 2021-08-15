package com.xyz.study.hmily.demo.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
@Data
public class ProductStockTryRecoverVO implements Serializable {
    private static final long serialVersionUID = -3074692557173981000L;
    private long productId;
    private int stockAmount;
}
