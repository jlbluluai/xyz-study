package com.xyz.study.multiDataSource.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Zhu WeiJie
 * @date 2021/8/7
 **/
@Aspect
@Component
public class MyRoutingDataSourceAspect {

    @Pointcut("(@annotation(DynamicDataSource))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void read(JoinPoint joinPoint) {
        // 根据动态数据源注解设置当前数据源
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DynamicDataSource source = method.getAnnotation(DynamicDataSource.class);
        MyRoutingDataSource.setDataSource(source.type());
    }

    @After("pointcut()")
    public void after(JoinPoint point) {
        //清理掉当前设置的数据源，让默认的数据源不受影响
        MyRoutingDataSource.clearDataSource();
    }

}
