package com.xyz.study.common.jike.week11.task9;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * 订单监听器配置
 *
 * @author Zhu WeiJie
 * @date 2021/9/2
 **/
@Configuration
public class OrderSubConfig {

    @Resource
    private OrderSub orderSub;

    @Bean
    RedisMessageListenerContainer orderCreateListener(final RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(new MessageListenerAdapter(orderSub), new ChannelTopic("order.create"));
        return container;
    }

}
