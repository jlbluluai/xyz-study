package com.xyz.study.common.jike.week5.task2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zhu WeiJie
 * @date 2021/7/24
 **/
@Configuration
public class BeanConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setId(1000001);
        student.setName("小叶子");
        return student;
    }
}
