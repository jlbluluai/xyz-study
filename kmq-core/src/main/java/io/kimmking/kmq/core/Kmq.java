package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public final class Kmq {

    private String topic;

    private int capacity;

    private MyQueue<KmqMessage<?>> queue;

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new MyQueue<>(capacity);
    }

    public void addConsumer(String consumerId) {
        queue.addConsumer(consumerId);
    }

    public boolean send(KmqMessage<?> message) {
        return queue.offer(message);
    }

    public KmqMessage<?> poll(String consumerId) {
        return queue.poll(consumerId);
    }

    @SneakyThrows
    public KmqMessage poll(String consumerId, long timeout) {
        return queue.poll(consumerId, timeout, TimeUnit.MILLISECONDS);
    }

}
