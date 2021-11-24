package com.xyz.study.common.jike.daily;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Zhu WeiJie
 * @date 2021/6/22
 **/
public class JVMClassLoaderAddUrl {

    public static void main(String[] args) {
        String appPath = "";
        URLClassLoader urlClassLoader = (URLClassLoader) JVMClassLoaderAddUrl.class.getClassLoader();
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            URL url = new URL(appPath);
            addURL.invoke(urlClassLoader,url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
