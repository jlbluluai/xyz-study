package com.xyz.foreign.exchange.account.dao;

import com.xyz.foreign.exchange.account.domain.FreezeRmb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FreezeRmbMapper {
    int insert(FreezeRmb record);

    FreezeRmb selectByPrimaryKey(Long id);

    int updateStatus(@Param("uid") long uid, @Param("exchangeId") long exchangeId, @Param("status") int status, @Param("now") long now);

    FreezeRmb selectByStatus(@Param("uid") long uid, @Param("exchangeId") long exchangeId, @Param("status") int status);

    int del(@Param("uid") long uid, @Param("exchangeId") long exchangeId, @Param("now") long now);
}