package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KmqDemo {

    @SneakyThrows
    public static void main(String[] args) {

        String topic = "kk.test";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);

        KmqConsumer consumer1 = broker.createConsumer();
        ConsumerDemo consumerDemo1 = new ConsumerDemo(consumer1, topic,"消费者1");
        new Thread(consumerDemo1).start();

        KmqConsumer consumer2 = broker.createConsumer();
        ConsumerDemo consumerDemo2 = new ConsumerDemo(consumer2, topic,"消费者2");
        new Thread(consumerDemo2).start();

        KmqProducer producer = broker.createProducer();
        for (int i = 0; i < 5; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            boolean send = producer.send(topic, new KmqMessage(null, order));
            if (!send) {
                log.warn("写入队列失败， order={}", order);
            }
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            char c = (char) System.in.read();
            if (c > 20) {
                System.out.println(c);
                producer.send(topic, new KmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            if (c == 'q' || c == 'e') break;
        }

        consumerDemo1.stop();
        consumerDemo2.stop();

    }
}
