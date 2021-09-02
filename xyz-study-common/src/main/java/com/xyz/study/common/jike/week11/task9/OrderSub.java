package com.xyz.study.common.jike.week11.task9;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * 订单订阅者
 *
 * @author Zhu WeiJie
 * @date 2021/9/2
 **/
@Service
public class OrderSub implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String s = new String(message.getBody());
        OrderAddVO orderAddVO = JSON.parseObject(s, OrderAddVO.class);
        System.out.println("添加订单：" + orderAddVO);
    }
}
