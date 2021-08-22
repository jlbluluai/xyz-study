package com.xyz.foreign.exchange.account.dao;

import com.xyz.foreign.exchange.account.domain.FreezeDollar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FreezeDollarMapper {
    int insert(FreezeDollar record);

    FreezeDollar selectByPrimaryKey(Long id);

    int updateStatus(@Param("uid") long uid, @Param("exchangeId") long exchangeId, @Param("status") int status, @Param("now") long now);

    FreezeDollar selectByStatus(@Param("uid") long uid, @Param("exchangeId") long exchangeId, @Param("status") int status);
}