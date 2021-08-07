package com.xyz.study.multiDataSource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 自定义路由数据源动态切换主从
 *
 * @author Zhu WeiJie
 * @date 2021/8/7
 **/
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DSTypeEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    public MyRoutingDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DSTypeEnum typeEnum = getDataSource();
        return typeEnum == null ? DSTypeEnum.master : typeEnum;
    }

    public static void setDataSource(DSTypeEnum dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    public static DSTypeEnum getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

}
