package com.xyz.study.jike;

import com.xyz.study.jike.bean.ISchool;
import com.xyz.study.jike.bean.Klass;
import com.xyz.study.jike.bean.School;
import com.xyz.study.jike.bean.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author Zhu WeiJie
 * @date 2021/7/24
 **/
@Configuration
public class BeanConfiguration {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * spring.auto.bean.kclass.enable 为 true时才加载bean
     */
    @ConditionalOnProperty(value = "spring.auto.bean.kclass.enable", havingValue = "true")
    @Bean
    public Klass class1() {
        Klass class1 = new Klass();
        class1.setStudents(Collections.singletonList((Student) applicationContext.getBean("student100")));
        return class1;
    }

    /**
     * 根据spring.auto.bean.student的配置属性自动注入
     */
    @ConfigurationProperties(prefix = "spring.auto.bean.student")
    @Bean(name = "student100")
    public Student student() {
        return new Student();
    }

    /**
     * class1 student100 bean均存在时才加载该bean
     */
    @ConditionalOnBean(name = {"class1", "student100"})
    @Bean
    public ISchool school() {
        return new School();
    }
}
