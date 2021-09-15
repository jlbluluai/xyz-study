package com.xyz.study.controller;

import com.xyz.study.service.KafkaProducerDemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/9/15
 **/
@RestController
@RequestMapping("/kafka/demo")
public class KafkaDemoController {

    @Resource
    private KafkaProducerDemo kafkaProducerDemo;

    @GetMapping("/send")
    public void send(
            @RequestParam String topic,
            @RequestParam String message
    ) {
        kafkaProducerDemo.send(topic, message);
    }
}
