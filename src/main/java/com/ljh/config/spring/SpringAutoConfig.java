package com.ljh.config.spring;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.swing.*;

//表明它是一个配置类
//@Configuration(proxyBeanMethods = false)
//自动配置属性：ServerProperties
//@EnableConfigurationProperties({WebMvcProperties.class})
public class SpringAutoConfig {

//    @Bean
//    @ConfigurationProperties("spring.mvc.pathmatch")
//    public WebMvcProperties.Pathmatch sxbhpDataSource() {
//        WebMvcProperties.Pathmatch pathmatch=new WebMvcProperties.Pathmatch();
//        pathmatch.
//    }


}
