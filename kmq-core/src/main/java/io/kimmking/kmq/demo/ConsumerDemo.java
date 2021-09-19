package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhu WeiJie
 * @date 2021/9/18
 **/
@Slf4j
public class ConsumerDemo implements Runnable {

    private KmqConsumer consumer;

    private boolean runFlag = true;

    private String name;

    public ConsumerDemo(KmqConsumer consumer, String topic, String name) {
        this.consumer = consumer;
        this.consumer.subscribe(topic);
        this.name = name;
    }

    @Override
    public void run() {
        while (runFlag) {
            KmqMessage<Order> message = consumer.poll(100);
            if (null != message) {
                log.info("{} 收到消息 message={}", name, message);
            }
        }
        log.info("{}停止", name);
    }

    public void stop() {
        this.runFlag = false;
    }
}

