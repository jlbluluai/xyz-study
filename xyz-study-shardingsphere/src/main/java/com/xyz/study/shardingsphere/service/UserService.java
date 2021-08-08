package com.xyz.study.shardingsphere.service;

import com.xyz.study.shardingsphere.dao.UserMapper;
import com.xyz.study.shardingsphere.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/8/7
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void add(User user) {
        userMapper.insert(user);
    }

    public User query(long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }


}
