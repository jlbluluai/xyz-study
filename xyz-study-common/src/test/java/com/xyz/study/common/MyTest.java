package com.xyz.study.common;

import com.xyz.study.common.jike.week11.task8.SimpleRedisCounter;
import com.xyz.study.common.jike.week11.task8.SimpleRedisLock;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class MyTest extends BaseTest {

    @Resource
    private SimpleRedisLock simpleRedisLock;

    @Test
    public void test1() {
        // 锁key
        String key = "lock.10001";

        // A获取锁 锁住5s
        long a = System.currentTimeMillis();
        boolean lock = simpleRedisLock.lock(key, a + "", 5, 0);
        System.out.println("A获取锁：" + lock + " ，当前时间：" + new Date());

        // B获取锁 等待2s 理论上B是获取不到锁的
        long b = System.currentTimeMillis();
        lock = simpleRedisLock.lock(key, b + "", 5, 2);
        System.out.println("B获取锁：" + lock + " ，当前时间：" + new Date());

        // 粗略来算 B等待了2s 离A自动释放锁还有3s
        // C获取锁 等待4s 理论上C会获取锁并且时间在B那条日志时间后的不到3s点
        long c = System.currentTimeMillis();
        lock = simpleRedisLock.lock(key, c + "", 5, 4);
        System.out.println("C获取锁：" + lock + " ，当前时间：" + new Date());

        // C获取锁 理论上5s后才释放 我们主动释放锁
        simpleRedisLock.unlock(key, c + "");

        // D获取锁 由于C主动释放锁 D应该是立马拿到锁
        long d = System.currentTimeMillis();
        lock = simpleRedisLock.lock(key, d + "", 5, 0);
        System.out.println("D获取锁：" + lock + " ，当前时间：" + new Date());
    }

    @Resource
    private SimpleRedisCounter simpleRedisCounter;

    @Test
    public void test2() {
        // 库存key
        String key = "inventory.10001";

        // 设定库存初始为10
        simpleRedisCounter.init(key, 10);

        // A使用库存4
        boolean flag = simpleRedisCounter.minus(key, 4);
        System.out.println("A：" + flag);
        System.out.println("剩余库存：" + simpleRedisCounter.get(key));

        // 此时平台调整控制库存最少5 B使用库存3
        flag = simpleRedisCounter.minus(key, 3, 5);
        System.out.println("B：" + flag);
        System.out.println("剩余库存：" + simpleRedisCounter.get(key));

        // 平台取消库存限制 C使用库存5
        flag = simpleRedisCounter.minus(key, 5);
        System.out.println("C：" + flag);
        System.out.println("剩余库存：" + simpleRedisCounter.get(key));

        // D使用库存2
        flag = simpleRedisCounter.minus(key, 2);
        System.out.println("D：" + flag);
        System.out.println("剩余库存：" + simpleRedisCounter.get(key));
    }

}
