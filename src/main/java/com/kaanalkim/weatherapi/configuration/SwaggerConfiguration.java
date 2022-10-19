package com.kaanalkim.weatherapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {
    @Configuration
    public static class SpringFoxConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
        }

        private ApiInfo apiInfo() {
            return new ApiInfo(
                    "Weather Forecast Sensorm APIs",
                    "An API Collection for serving and collection weather data ",
                    "1.0",
                    "Terms of service",
                    new Contact("Kaan Alkim", "https://github.com/gunerkaanalkim", "g.kaanalkim@gmail.com"),
                    "License of API",
                    "API license URL",
                    Collections.emptyList());
        }
    }
}