package com.xyz.study.multiDataSource.dao;

import com.xyz.study.multiDataSource.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);
}