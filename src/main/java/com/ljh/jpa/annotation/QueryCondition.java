package com.ljh.jpa.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动构建查询参数
 * @date 2021/1/26 11:17
 * @since 0.2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {

    String value() default "";


    /**
     * 条件关系 eq、like、lt、gt
     * @date 2021/1/26 11:17
     * @since 0.1.1
     */
    QueryRelation relation() default QueryRelation.EQ;

    String colName() default "";

    String defaultValue() default "";

    String group() default "";
}
