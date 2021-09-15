package com.xyz.study.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Zhu WeiJie
 * @date 2021/9/14
 **/
@Service
@Slf4j
public class KafkaConsumerDemo {

    @KafkaListener(topics = "xyz.test")
    public void receiveTest(ConsumerRecord<?, ?> record) {
        log.info("receive message, topic={}, value={}", record.topic(), record.value());
    }

}
