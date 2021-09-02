package com.xyz.study.common.jike.week11.task9;

import com.alibaba.fastjson.JSON;
import com.xyz.study.common.cache.MyRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单发布者
 *
 * @author Zhu WeiJie
 * @date 2021/9/2
 **/
@RestController
public class OrderPub {

    @Resource
    private MyRedisTemplate myRedisTemplate;

    @PostMapping("/order/pub")
    public void pubOrder(@RequestBody OrderAddVO orderAddVO){
        myRedisTemplate.originalTemplate().convertAndSend("order.create", JSON.toJSONString(orderAddVO));
    }
}
