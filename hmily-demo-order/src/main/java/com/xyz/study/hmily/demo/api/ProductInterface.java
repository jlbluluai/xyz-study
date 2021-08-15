package com.xyz.study.hmily.demo.api;

import com.xyz.study.hmily.demo.api.vo.ProductStockTryRecoverVO;
import com.xyz.study.hmily.demo.api.vo.ProductStockUseVO;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
public interface ProductInterface {

    void useStock(ProductStockUseVO useVO);

    void tryRecoverStock(ProductStockTryRecoverVO paramVO);
}
