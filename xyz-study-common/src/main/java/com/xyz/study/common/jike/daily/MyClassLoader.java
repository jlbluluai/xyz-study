package com.xyz.study.common.jike.daily;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author Zhu WeiJie
 * @date 2021/6/23
 **/
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> hello = new MyClassLoader().findClass("Hello");
        Object o = hello.newInstance();

        Method method = hello.getMethod("hello");
        method.invoke(o, null);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("Hello.xlass");
        long length = file.length();
        // 作业写法 一次性读完
        byte[] buf = new byte[(int) length];
        try (InputStream input = new FileInputStream(file)) {
            input.read(buf);

            for (int i = 0; i < length; i++) {
                buf[i] = (byte) (255 - buf[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defineClass(name, buf, 0, buf.length);
    }
}
