package com.xyz.study.hmily.demo;

import com.xyz.study.hmily.demo.api.OrderInterface;
import com.xyz.study.hmily.demo.api.vo.OrderAddVO;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class MyTest extends BaseTest {

    @Resource
    private OrderInterface orderInterface;

    @Test
    public void testOrder(){
        OrderAddVO addVO = new OrderAddVO();
        addVO.setProductId(1);
        addVO.setStockAmount(10);
        orderInterface.placeAnOrder(addVO);
    }

}
