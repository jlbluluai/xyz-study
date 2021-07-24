package com.xyz.study.jike.week5.task2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Zhu WeiJie
 * @date 2021/7/24
 **/
public class Week5Task2Demo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("com.xyz.study.jike.week5.task2/spring-beans.xml");

        // xml配置bean
        User user = (User) context.getBean("user");
        System.out.println(user);

        // Annotation自动装配
        UserService userService = (UserService) context.getBean("userService");
        userService.printUser(user);

        // java代码配置bean
        Student student = (Student) context.getBean("student");
        System.out.println(student);
    }
}
