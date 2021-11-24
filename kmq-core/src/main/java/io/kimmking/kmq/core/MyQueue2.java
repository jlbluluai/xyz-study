package io.kimmking.kmq.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhu WeiJie
 * @date 2021/9/16
 **/
@Slf4j
public class MyQueue2<T> {

    private T[] queue;

    private int capacity;

    /**
     * 当前读的位置
     */
    private int currentReadIndex = 0;

    /**
     * 当前写位置
     */
    private int currentWriteIndex = 0;

    /**
     * 实际数据量
     */
    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();

    public MyQueue2(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("队列容量不得为空");
        }
        this.capacity = capacity;
        this.queue = (T[]) new Object[capacity];
    }

    /**
     * 队列入值
     */
    public boolean offer(T message) {
        lock.lock();
        try {
            // 容量已满
            if (count == capacity) {
                return false;
            }

            // 容量不满，写入
            queue[currentWriteIndex] = message;

            // 写完后，写指针移动
            if (currentWriteIndex < capacity - 1) {
                currentWriteIndex++;
            } else {
                currentWriteIndex = 0;
            }
            count++;
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 队列出值
     */
    public T poll() {
        lock.lock();
        try {
            // 容量无
            if (count == 0) {
                return null;
            }

            // 容量有，读取
            T t = queue[currentReadIndex];

            // 移动读指针
            if (currentReadIndex < capacity - 1) {
                currentReadIndex++;
            } else {
                currentReadIndex = 0;
            }
            count--;

            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 队列出值，带超时
     */
    public T poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
        try {
            boolean flag = lock.tryLock(timeout, timeUnit);
            if (!flag) {
                return null;
            }

            // 容量无
            if (count == 0) {
                return null;
            }

            // 容量有，读取
            T t = queue[currentReadIndex];

            // 移动读指针
            if (currentReadIndex < capacity - 1) {
                currentReadIndex++;
            } else {
                currentReadIndex = 0;
            }
            count--;
            return t;
        } finally {
            lock.unlock();
        }
    }


}
