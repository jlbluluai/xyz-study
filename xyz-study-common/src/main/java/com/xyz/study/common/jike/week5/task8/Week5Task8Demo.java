package com.xyz.study.common.jike.week5.task8;

import com.xyz.study.jike.bean.ISchool;
import com.xyz.study.jike.bean.Klass;
import com.xyz.study.jike.bean.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Zhu WeiJie
 * @date 2021/7/24
 **/
@SpringBootApplication
public class Week5Task8Demo {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Week5Task8Demo.class, args);

        // 获取starter中的bean进行相关的操作
        Student student = (Student) context.getBean("student100");
        System.out.println(student);

        Klass class1 = (Klass) context.getBean("class1");
        System.out.println(class1);

        ISchool school = (ISchool) context.getBean(ISchool.class);
        System.out.println(school);
    }
}
