package com.xyz.study.shardingsphere.dao;

import com.xyz.study.shardingsphere.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);
}