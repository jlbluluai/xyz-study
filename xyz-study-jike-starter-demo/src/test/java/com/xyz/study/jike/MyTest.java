package com.xyz.study.jike;

import com.xyz.study.jike.bean.ISchool;
import com.xyz.study.jike.bean.Klass;
import com.xyz.study.jike.bean.Student;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class MyTest extends BaseTest{

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test1(){
        Student student = (Student) applicationContext.getBean("student100");
        System.out.println(student);

        Klass class1 = (Klass) applicationContext.getBean("class1");
        System.out.println(class1);

        ISchool school = (ISchool) applicationContext.getBean(ISchool.class);
        System.out.println(school);
    }
}
