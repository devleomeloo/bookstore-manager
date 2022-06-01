package com.leonardo.bookstoremanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String NAME = "Leonardo Melo";
    private static final String URL = "https://github.com/devleomeloo/bookstore-manager";
    private static final String EMAIL = "lmelojesus@live.com";
    private static final String BASE_PACKAGE = "com.leonardo.bookstoremanager";
    private static final String VERSION = "1.0.0";
    private static final String TITLE = "BookStore Manager";
    private static final String DESCRIPTION = "BookStore REST API";

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build().apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .contact(new Contact(NAME, URL, EMAIL))
                .build();
    }
}
