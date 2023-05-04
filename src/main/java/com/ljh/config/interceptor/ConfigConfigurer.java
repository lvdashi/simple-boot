package com.ljh.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注册
 * @author lvjinhui
 * @date 2022/03/03 17:41
 **/
@Configuration
public class ConfigConfigurer implements WebMvcConfigurer {
    @Bean
    public TokenValidatorInterceptor getTokenValidatorInterceptor() {
        return new TokenValidatorInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(getTokenValidatorInterceptor())
                //拦截所有请求
                .addPathPatterns("/**")
                //不拦截的路径/放行swagger请求
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/doc.html/**","/swagger-ui/**")
                .excludePathPatterns("/error")
                //放行开放接口
                .excludePathPatterns("/open/**")
                //放行actuator监控
                .excludePathPatterns("/actuator/**")
        ;
    }



    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        //解决swagger无法访问 解决swagger的js文件无法访问
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置//设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //设置允许的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                //请求头
                .allowedHeaders("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //跨域允许时间
                .maxAge(3600);
    }
}
