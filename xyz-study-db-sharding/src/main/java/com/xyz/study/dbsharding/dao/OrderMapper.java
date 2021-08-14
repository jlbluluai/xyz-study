package com.xyz.study.dbsharding.dao;

import com.xyz.study.dbsharding.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int insert(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Order record);

    List<Order> selectListByUid(@Param("uid") long uid);

    Order selectByUidAndId(@Param("uid") long uid, @Param("id") long id);

    int delete(@Param("id") long id);
}