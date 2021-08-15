package com.xyz.study.hmily.demo.controller;

import com.xyz.study.hmily.demo.api.OrderInterface;
import com.xyz.study.hmily.demo.api.vo.OrderAddVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 林区-书籍
 *
 * @author xyz
 * @date 2020-05-12
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private OrderInterface orderInterface;

    @GetMapping(value = "/order")
    public void add() {
        OrderAddVO addVO = new OrderAddVO();
        addVO.setProductId(1);
        addVO.setStockAmount(10);

        orderInterface.placeAnOrder(addVO);
    }

}
