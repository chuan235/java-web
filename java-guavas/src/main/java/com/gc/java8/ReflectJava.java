package com.gc.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * java8中新增获取方法名，获取方法参数名
 * 虚拟机：移除了PermGen,取而代之的是Metaspace
 *      -XX:PermSize 与 -XX:MaxPermSize
 *   被 -XX:MetaspaceSize 与 -XX:MaxMetaspaceSize 取代
 */
public class ReflectJava {

    public void method(String s1,String s2){

    }

    public static void getParams(){
        Class<ReflectJava> clazz = ReflectJava.class;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals("method")) {
                // 获取参数
                for (Parameter parameter : method.getParameters()) {
                    // 参数名称 s1,s2
                    System.out.println(parameter.getName());
                }
                // 获取参数个数
                System.out.println("参数总数="+method.getParameterCount());
            }

        }
    }
}
