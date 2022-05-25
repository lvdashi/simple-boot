package com.ljh.api;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lvjinhui
 * @desc 接口日志注解，用于AOP获取接口描述
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLogAndCommonException {
    //接口描述,可以优先使用swagger的注释
    String desc() default "未描述";
    //是否打印接口返回值
    boolean printRes() default true;
}
