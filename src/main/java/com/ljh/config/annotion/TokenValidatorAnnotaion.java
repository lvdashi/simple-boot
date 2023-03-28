package com.ljh.config.annotion;

import java.lang.annotation.*;

/**
 * Token验证注解
 * 开放接口可加上本注解去除Token认证
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenValidatorAnnotaion {
    //是否对token进行校验，默认需要
    boolean required() default true;
}
