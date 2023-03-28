package com.ljh.config.annotion;

import java.lang.annotation.*;

/**
 * 权限验证注解
 *
 * @author jinhuilv
 * @date 2023/03/28 21:53
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleVaildator {
    //权限字段JSON数组
    String roles() default "";
}
