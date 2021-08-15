package com.xyz.study.hmily.demo.dao;

import com.xyz.study.hmily.demo.domain.Order;

public interface OrderMapper {
    int insert(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Order record);
}