package com.xyz.study.hmily.demo.service;

import com.xyz.study.hmily.demo.api.ProductInterface;
import com.xyz.study.hmily.demo.api.vo.ProductStockTryRecoverVO;
import com.xyz.study.hmily.demo.api.vo.ProductStockUseVO;
import com.xyz.study.hmily.demo.dao.ProductMapper;
import com.xyz.study.hmily.demo.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
@Service
@Slf4j
public class ProductService implements ProductInterface {

    @Resource
    private ProductMapper productMapper;

    @HmilyTCC(confirmMethod = "useConfirm", cancelMethod = "useCancel")
    @Override
    public void useStock(ProductStockUseVO useVO) {
        // check
        Product product = productMapper.selectByPrimaryKey(useVO.getProductId());
        if (product.getStockAmount() - useVO.getStockAmount() < 0) {
            throw new RuntimeException("库存不够");
        }

        // update
        productMapper.useStock(useVO.getProductId(), useVO.getStockAmount());
    }

    @Override
    public void tryRecoverStock(ProductStockTryRecoverVO paramVO) {
        // 真实情况需核对有没有没有订单的库存占用，有则走取消占用流程
        // 该处简单实现加库存模拟
        productMapper.useStock(paramVO.getProductId(), -paramVO.getStockAmount());
        System.out.println("尝试恢复库存结束");
    }

    public void useConfirm(ProductStockUseVO useVO){
        System.out.println("confirm");
    }

    public void useCancel(ProductStockUseVO useVO){
        System.out.println("cancel");
    }

}
