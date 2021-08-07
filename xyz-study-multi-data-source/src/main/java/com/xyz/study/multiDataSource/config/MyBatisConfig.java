package com.xyz.study.multiDataSource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author Zhu WeiJie
 * @date 2021/8/7
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.xyz.study.multiDataSource.dao"}, sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class MyBatisConfig {

    @Resource(name = "dynamicDataSource")
    private DataSource dynamicDataSource;

    @Bean("dynamicSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
