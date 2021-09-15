package com.xyz.study.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/9/14
 **/
@Service
@Slf4j
public class KafkaProducerDemo {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息
     */
    public void send(String topic, Object value) {
        String message = JSON.toJSONString(value);
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                //发送失败的处理
                log.error("send msg error, topic={}, message={}", topic, message, ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                //成功的处理
                log.info("send msg success, topic={}, message={}, result={}", topic, message, result.toString());
            }
        });
    }

}
