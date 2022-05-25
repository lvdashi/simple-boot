package com.ljh.config.spring;

import cn.hutool.core.comparator.VersionComparator;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring配置
 */
@Configuration
public class SpringAutoConfig implements WebMvcConfigurer {


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        String springBootVersion = SpringBootVersion.getVersion();
        if(VersionComparator.INSTANCE.compare(springBootVersion,"2.6.0")>0){//当前springboot版本高于2.6.0
            //修改spring mvc处理映射匹配的默认策略
            AntPathMatcher antPathMatcher=new AntPathMatcher();
            //antPathMatcher.setPathSeparator();
            configurer.setPathMatcher(antPathMatcher);
        }
        WebMvcConfigurer.super.configurePathMatch(configurer);
    }
}
