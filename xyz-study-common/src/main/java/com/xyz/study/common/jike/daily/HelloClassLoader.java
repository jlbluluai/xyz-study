package com.xyz.study.common.jike.daily;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * @author Zhu WeiJie
 * @date 2021/6/22
 **/
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> helloByte =  new HelloClassLoader().findClass("com.xyz.linzone.study.jike.HelloByteCode");
        Object o = helloByte.newInstance();

        Method method = helloByte.getMethod("sayHi");
        method.invoke(o,null);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String helloBase64 = "yv66vgAAADQAKQoACAAaCQAbABwIAB0KAB4AHwcAIAoABQAaCAAhBwAiAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBACpMY29tL3h5ei9saW56b25lL3N0dWR5L2ppa2UvSGVsbG9CeXRlQ29kZTsBAAVzYXlIaQEABG1haW4BABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAEYXJncwEAE1tMamF2YS9sYW5nL1N0cmluZzsBAA1oZWxsb0J5dGVDb2RlAQAQTWV0aG9kUGFyYW1ldGVycwEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEAEkhlbGxvQnl0ZUNvZGUuamF2YQwACQAKBwAjDAAkACUBAAJIaQcAJgwAJwAoAQAoY29tL3h5ei9saW56b25lL3N0dWR5L2ppa2UvSGVsbG9CeXRlQ29kZQEABUhlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUACAAAAAAABAABAAkACgABAAsAAAAvAAEAAQAAAAUqtwABsQAAAAIADAAAAAYAAQAAAAcADQAAAAwAAQAAAAUADgAPAAAAAQAQAAoAAQALAAAANwACAAEAAAAJsgACEgO2AASxAAAAAgAMAAAACgACAAAADgAIAA8ADQAAAAwAAQAAAAkADgAPAAAACQARABIAAgALAAAAQQACAAIAAAAJuwAFWbcABkyxAAAAAgAMAAAACgACAAAAEgAIABMADQAAABYAAgAAAAkAEwAUAAAACAABABUADwABABYAAAAFAQATAAAACAAXAAoAAQALAAAAJQACAAAAAAAJsgACEge2AASxAAAAAQAMAAAACgACAAAACgAIAAsAAQAYAAAAAgAZ";
        byte[] bytes = decode(helloBase64);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
