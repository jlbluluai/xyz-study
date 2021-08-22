package com.xyz.foreign.exchange.account.dao;

import com.xyz.foreign.exchange.account.domain.AccountRmb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountRmbMapper {
    AccountRmb selectByPrimaryKey(Long id);

    List<AccountRmb> selectByUidsForUpdate(@Param("uidList") List<Long> uidList);

    AccountRmb selectByUidForUpdate(@Param("uid") long uid);

    int updateBalance(@Param("id") long id, @Param("balance") long balance, @Param("now") long now);
}