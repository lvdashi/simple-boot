package com.ljh.config.annotion;

import java.lang.annotation.*;

/**
 * 权限验证注解
 * 开放接口可加上本注解去除权限认证
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenValidatorAnnotaion {
    //是否对token进行校验，默认需要
    boolean required() default true;
}
