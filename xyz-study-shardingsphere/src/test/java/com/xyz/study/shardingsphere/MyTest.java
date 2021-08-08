package com.xyz.study.shardingsphere;

import com.xyz.study.shardingsphere.domain.User;
import com.xyz.study.shardingsphere.service.UserService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class MyTest extends BaseTest {

    @Resource
    private UserService userService;

    @Test
    public void testUser(){
        User user = new User();
        user.setUserName("test2");
        user.setPassword("123");
        user.setNickname("测试2");
        user.setPhone("13676456568");
        user.setMail("test2@qq.com");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        userService.add(user);
        System.out.println(user.getId());

        User slaveUser = userService.query(user.getId());
        System.out.println(slaveUser);
    }

}
