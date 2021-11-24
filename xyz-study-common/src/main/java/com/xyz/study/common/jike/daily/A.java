package com.xyz.study.common.jike.daily;

/**
 * @author Zhu WeiJie
 * @date 2021/7/2
 **/
public class A {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof A){
            A a = (A)obj;
            return this.id == a.id;
        }

        return false;
    }


    public static void main(String[] args) {
        String s1= "22";

        String s2= "22";

        System.out.println(s1.hashCode());

        System.out.println(s2.hashCode());

        System.out.println(s1==s2);


    }
}
