package com.xyz.study.common.jike.week11.task8;

import com.xyz.study.common.cache.MyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 简单Redis分布式锁
 *
 * @author Zhu WeiJie
 * @date 2021/9/1
 **/
@Component
@Slf4j
public class SimpleRedisLock {

    @Resource
    private MyRedisTemplate myRedisTemplate;

    /**
     * 获取锁
     *
     * @param key         key
     * @param value       value
     * @param exSeconds   锁住时长 单位：s
     * @param waitSeconds 若未获取锁，尝试时长 单位：s 没秒尝试一次
     * @return true/false
     */
    public boolean lock(String key, String value, int exSeconds, int waitSeconds) {
        int wait = 0;
        do {
            boolean lock = myRedisTemplate.setexnx(key, value, exSeconds);
            if (lock) {
                return true;
            } else {
                if (waitSeconds > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        log.error("lock wait for sleep failed.", e);
                    }
                    wait++;
                }
            }
        } while (wait < waitSeconds);

        return false;
    }

    /**
     * 释放锁
     *
     * @param key   key
     * @param value value
     */
    public boolean unlock(String key, String value) {
        // 通过lua脚本，（获取锁值匹配再删除，做成原子操作），防止释放的锁有误
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then \n" +
                "    return redis.call(\"del\",KEYS[1]) \n" +
                "else\n" +
                "    return 0 \n" +
                "end";
        Long result = myRedisTemplate.eval(script, Long.class, Collections.singletonList(key), new String[]{value});
        return result != null && result == 1;
    }

}
