package com.xyz.study.common.jike.week11.task8;

import com.xyz.study.common.cache.MyRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 简单Redis分布式计数器
 *
 * @author Zhu WeiJie
 * @date 2021/9/2
 **/
@Component
public class SimpleRedisCounter {

    @Resource
    private MyRedisTemplate myRedisTemplate;

    /**
     * 初始化
     *
     * @param key   key
     * @param quota 配额数量
     */
    public void init(String key, long quota) {
        myRedisTemplate.set(key, String.valueOf(quota));
    }

    /**
     * 获取当前计数
     *
     * @param key key
     */
    public long get(String key) {
        String data = myRedisTemplate.get(key);
        return Long.parseLong(data);
    }

    /**
     * 加
     *
     * @param key   key
     * @param quota 配额数量
     * @return true/flase
     */
    public boolean plus(String key, long quota) {
        Long increment = myRedisTemplate.originalTemplate().opsForValue().increment(key, quota);
        return increment != null;
    }

    /**
     * 减（默认最低限制为0，即减完不得低于0）
     *
     * @param key   key
     * @param quota 配额数量
     * @return true/false
     */
    public boolean minus(String key, long quota) {
        String script = "local value = redis.call(\"get\",KEYS[1])\n" +
                "if(value == nil or value == 0 or value - ARGV[1] < 0) \n" +
                "then \n" +
                "    return nil\n" +
                "else \n" +
                "    return redis.call(\"decrby\",KEYS[1],ARGV[1])\n" +
                "end";
        Long result = myRedisTemplate.eval(script, Long.class, Collections.singletonList(key), new String[]{String.valueOf(quota)});
        return result != null;
    }

    /**
     * 减（自定义最低限制，即减完不低于该值即可）
     *
     * @param key   key
     * @param quota 配额数量
     * @param min   最低限制
     * @return true/false
     */
    public boolean minus(String key, long quota, long min) {
        String script = "local value = redis.call(\"get\",KEYS[1])\n" +
                "if(value == nil or value == " + min + " or value - ARGV[1] < " + min + ") \n" +
                "then \n" +
                "    return nil\n" +
                "else \n" +
                "    return redis.call(\"decrby\",KEYS[1],ARGV[1])\n" +
                "end";
        Long result = myRedisTemplate.eval(script, Long.class, Collections.singletonList(key), new String[]{String.valueOf(quota)});
        return result != null;
    }
}
