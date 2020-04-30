package com.webtv.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class ApiDocsConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.webtv"))           
          .paths(PathSelectors.ant("/api/v1/**"))                          
          .build()
          .apiInfo(apiInfo());                                        
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "WebTv API", 
          "Webtv API is a REST web service for uploading and streaming a video. It serve as a tool for campagne.", 
          "v1", 
          "http://www.webttv.com", 
          new Contact("Abned", "http://www.abned.com", "abned@abned.com"), 
          "License of API", "http://www.abned.com/api-licence.html", Collections.emptyList());
    }
}