package com.xyz.study;

import com.xyz.study.multiDataSource.domain.User;
import com.xyz.study.multiDataSource.service.UserService;
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
        user.setUserName("test");
        user.setPassword("123");
        user.setNickname("测试");
        user.setPhone("13676456567");
        user.setMail("test@qq.com");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        userService.add(user);
        System.out.println(user.getId());

        User slaveUser = userService.query(user.getId());
        System.out.println(slaveUser);
    }
}
