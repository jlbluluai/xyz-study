package com.xyz.study.hmily.demo.dao;

import com.xyz.study.hmily.demo.domain.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Product record);

    int useStock(@Param("id") long id, @Param("stockAmount") int stockAmount);
}