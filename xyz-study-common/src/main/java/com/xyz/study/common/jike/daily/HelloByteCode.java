package com.xyz.study.common.jike.daily;

/**
 * @author Zhu WeiJie
 * @date 2021/6/21
 **/
public class HelloByteCode {

    static {
        System.out.println("Hello");
    }

    public void sayHi() {
        System.out.println("Hi");
    }

    public static void main(String[] args) {
        HelloByteCode helloByteCode = new HelloByteCode();
    }
}
