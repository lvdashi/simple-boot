package com.ljh.config.swagger;

import com.ljh.util.ConsolePrintUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * swagger配置
 *
 * @author lvjinhui
 * @date 2021/7/6 16:06
 * @since 0.3.3
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${simple-boot.swagger.title}")
    private String title;
    @Value("${simple-boot.swagger.version}")
    private String version;
    @Value("${simple-boot.swagger.controller.path}")
    private String path;

    @Bean
    public Docket restApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(version)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(version + "接口说明")
                        .version(version)
                        .build())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(path))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(Collections.singletonList(new RequestParameterBuilder()
                        .name("token")
                        .description(" token 信息 ")
                        .in(ParameterType.HEADER)
                        .required(true)
                        .build()))
                ;
    }



}