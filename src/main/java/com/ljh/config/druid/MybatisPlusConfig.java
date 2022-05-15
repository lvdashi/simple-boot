package com.ljh.config.druid;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 * @author lvjinhui
 * @Date 2022-05-11
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * mybatid-plus分页设置
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor=new PaginationInnerInterceptor();
        //设置页面请求最大页后操作，true 返回首页 false会继续请求
        paginationInnerInterceptor.setOverflow(true);
        //设置单页最大数量
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }
}
