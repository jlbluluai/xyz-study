package com.xyz.foreign.exchange.transaction.dao;

import com.xyz.foreign.exchange.transaction.domain.ForeignExchange;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForeignExchangeMapper {
    int insert(ForeignExchange record);

    ForeignExchange selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ForeignExchange record);
}