package com.xyz.study.multiDataSource.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源动态切换
 *
 * @author Zhu WeiJie
 * @date 2021/8/7
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDataSource {

    DSTypeEnum type() default DSTypeEnum.master;
}
