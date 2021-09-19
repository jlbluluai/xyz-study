package io.kimmking.kmq.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhu WeiJie
 * @date 2021/9/16
 **/
@Slf4j
public class MyQueue<T> {

    private T[] queue;

    private int capacity;

    /**
     * 当前写位置
     */
    private int currentWriteIndex = 0;

    private ConcurrentHashMap<String, Integer> consumers = new ConcurrentHashMap<>();

    /**
     * 写锁
     */
    private ReentrantLock lock = new ReentrantLock();

    private ConcurrentHashMap<String, ReentrantLock> readLockMap = new ConcurrentHashMap<>();


    public MyQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("队列容量不得为空");
        }
        this.capacity = capacity;
        this.queue = (T[]) new Object[capacity];
    }

    /**
     * 增加消费者
     */
    public void addConsumer(String consumerId) {
        consumers.put(consumerId, 0);
        readLockMap.put(consumerId, new ReentrantLock());
    }

    /**
     * 队列入值
     */
    public boolean offer(T message) {
        lock.lock();
        try {
            // 容量已满
            if (currentWriteIndex == capacity) {
                return false;
            }

            // 容量不满，写入
            queue[currentWriteIndex++] = message;

            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 队列出值
     */
    public T poll(String consumerId) {
        ReentrantLock lock = readLockMap.get(consumerId);
        lock.lock();
        try {
            // 拿出当前消费者读指针
            int currentReadIndex = consumers.get(consumerId);

            // 无数据 当前读追上了当前写，无数据可读
            if (currentReadIndex == currentWriteIndex) {
                return null;
            }

            // 容量有，读取
            T t = queue[currentReadIndex];
            consumers.put(consumerId, ++currentReadIndex);

            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 队列出值，带超时
     */
    public T poll(String consumerId, long timeout, TimeUnit timeUnit) throws InterruptedException {
        ReentrantLock lock = readLockMap.get(consumerId);
        try {
            boolean flag = lock.tryLock(timeout, timeUnit);

            if (!flag) {
                return null;
            }

            // 拿出当前消费者读指针
            int currentReadIndex = consumers.get(consumerId);

            // 无数据 当前读追上了当前写，无数据可读
            if (currentReadIndex == currentWriteIndex) {
                return null;
            }

            // 容量有，读取
            T t = queue[currentReadIndex];
            consumers.put(consumerId, ++currentReadIndex);
            return t;
        } finally {
            lock.unlock();
        }
    }


}
