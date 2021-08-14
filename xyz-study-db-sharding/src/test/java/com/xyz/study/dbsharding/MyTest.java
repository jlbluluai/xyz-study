package com.xyz.study.dbsharding;

import com.xyz.study.dbsharding.domain.Order;
import com.xyz.study.dbsharding.service.OrderService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class MyTest extends BaseTest {

    @Resource
    private OrderService orderService;

    @Test
    public void testOrder(){
//        List<Order> orders = orderService.query(10001);
//        System.out.println(orders);

//        Order order = orderService.query(10001, 2);
//        System.out.println(order);

        Order order = new Order();
        order.setUid(100001L);
        order.setProductId(1L);
        order.setProductAmount(1);
        order.setOrderStatus(0);
        order.setOrderPrice(1L);
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        orderService.add(order);

        System.out.println(order);
    }

}
