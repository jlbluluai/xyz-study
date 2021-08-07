package com.xyz.study.multiDataSource.service;

import com.xyz.study.multiDataSource.config.DSTypeEnum;
import com.xyz.study.multiDataSource.config.DynamicDataSource;
import com.xyz.study.multiDataSource.dao.UserMapper;
import com.xyz.study.multiDataSource.domain.User;
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

    /**
     * 不指定数据源默认就是master
     */
    public void add(User user) {
        userMapper.insert(user);
    }

    @DynamicDataSource(type = DSTypeEnum.slave)
    public User query(long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
