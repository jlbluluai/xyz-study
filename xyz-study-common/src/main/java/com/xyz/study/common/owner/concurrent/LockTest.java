package com.xyz.study.common.owner.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhu WeiJie
 * @date 2021/7/27
 **/
@Slf4j
public class LockTest {

    /**
     * 定义锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 定义条件
     */
    private final Condition condition = lock.newCondition();

    /**
     * 测试lock()
     */
    public void testLock(int a) {
        try {
            lock.lock();
            log.info("time={} a={}", LocalDateTime.now(), a);
            sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void testLockInterrupt(int a) {
        try {
            lock.lockInterruptibly();
            log.info("time={} a={}", LocalDateTime.now(), a);
            sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void testTryLock(int a) {
        log.info("try get lock a={} now={}", a, LocalDateTime.now());
        boolean flag = lock.tryLock();
        if (!flag) {
            log.info("cannot get lock a={} now={}", a, LocalDateTime.now());
            return;
        }
        log.info("time={} a={}", LocalDateTime.now(), a);
        sleep(5000);
        lock.unlock();
    }

    public void testTryLockWithTimeout(int a) {
        log.info("try get lock a={} now={}", a, LocalDateTime.now());
        boolean flag = false;
        try {
            flag = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!flag) {
            log.info("cannot get lock a={} now={}", a, LocalDateTime.now());
            return;
        }
        log.info("time={} a={}", LocalDateTime.now(), a);
        sleep(5000);
        lock.unlock();
    }

    public void testNewCondition(int a) {
        try {
            lock.lock();
            if (a == 1) {
                condition.await();
            }
            log.info("time={} a={}", LocalDateTime.now(), a);
            sleep(2000);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        LockTest lockTest = new LockTest();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        for (int i = 0; i < 2; i++) {
//            int finalI = i;
//            executorService.execute(() -> lockTest.testLock(finalI));
//        }
//
//        executorService.shutdown();

        Thread t1 = new Thread(() -> lockTest.testNewCondition(1));
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> lockTest.testNewCondition(2));
        t2.start();
    }
}
