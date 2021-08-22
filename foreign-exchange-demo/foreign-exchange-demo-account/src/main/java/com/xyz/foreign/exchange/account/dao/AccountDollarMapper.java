package com.xyz.foreign.exchange.account.dao;

import com.xyz.foreign.exchange.account.domain.AccountDollar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountDollarMapper {
    AccountDollar selectByPrimaryKey(Long id);

    List<AccountDollar> selectByUidsForUpdate(@Param("uidList") List<Long> uidList);

    AccountDollar selectByUidForUpdate(@Param("uid") long uid);

    int updateBalance(@Param("id") long id, @Param("balance") long balance, @Param("now") long now);
}