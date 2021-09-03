package com.xyz.study.common.jike.week11.task9;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderSub implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String s = new String(message.getBody());
        OrderAddVO orderAddVO = JSON.parseObject(s, OrderAddVO.class);
        log.info("添加订单 param={}", orderAddVO);
    }
}
