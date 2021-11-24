package com.xyz.study.common.jike.daily;

/**
 * @author Zhu WeiJie
 * @date 2021/6/23
 **/
public class Hello {

    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a,int b) {
        this.a = a;
    }

    public Hello(int a) {
        this.a = a;
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 1;

        int sum = 0;
        for (int i = 0; i < 100; i++) {
            if (sum > 200) {
                break;
            }
            sum += a * b;
            a++;
            b = b + 2;
        }
    }
}