package com.xyz.study;

import com.xyz.study.service.KafkaProducerDemo;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/9/15
 **/
public class MyTest extends BaseTest {

    @Resource
    private KafkaProducerDemo kafkaProducerDemo;

    @Test
    public void testKafka() throws InterruptedException {
        kafkaProducerDemo.send("xyz.test", 1);
        Thread.sleep(10000);
    }
}
