package com.xyz.study.hmily.demo.service;

import com.xyz.study.hmily.demo.api.OrderInterface;
import com.xyz.study.hmily.demo.api.ProductInterface;
import com.xyz.study.hmily.demo.api.vo.OrderAddVO;
import com.xyz.study.hmily.demo.api.vo.ProductStockTryRecoverVO;
import com.xyz.study.hmily.demo.api.vo.ProductStockUseVO;
import com.xyz.study.hmily.demo.dao.OrderMapper;
import com.xyz.study.hmily.demo.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
@Service
@Slf4j
public class OrderService implements OrderInterface {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductInterface productService;

    @HmilyTCC(confirmMethod = "orderConfirm", cancelMethod = "orderCancel")
    @Transactional
    @Override
    public void placeAnOrder(OrderAddVO addVO){
        long productId = addVO.getProductId();
        int amount = addVO.getStockAmount();

        // 扣减库存
        ProductStockUseVO useVO = new ProductStockUseVO();
        useVO.setProductId(productId);
        useVO.setStockAmount(amount);
        productService.useStock(useVO);

        // 下单
        Order order = new Order();
        order.setUid(1000L);
        order.setProductId(productId);
        order.setProductAmount(amount);
        order.setOrderStatus((byte) 1);
        order.setOrderPrice(100L);
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        orderMapper.insert(order);
        int i =1/0;
    }

    public void orderConfirm(OrderAddVO useVO){
        System.out.println("confirm");
    }

    public void orderCancel(OrderAddVO useVO){
        System.out.println("cancel");
        // 尝试恢复库存
        ProductStockTryRecoverVO paramVO = new ProductStockTryRecoverVO();
        paramVO.setProductId(1);
        paramVO.setStockAmount(10);
        productService.tryRecoverStock(paramVO);
    }

}
