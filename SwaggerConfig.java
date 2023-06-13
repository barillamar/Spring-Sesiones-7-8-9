package com.example.Sesiones_7_8_9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

// http://localhost:8008/swagger-ui/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiDetails() {
        return new ApiInfo("Spring Boot Laptop Api Rest",
                "Laptop Store Api Rest Docs",
                "1.0",
                "http://www.Google.com",
                new Contact("Maferzuki", "http://www.Google.com", "maferzuki@gmail.com"),
                "MIT License",
                "http://www.Google.com",
                Collections.emptyList() );
    }
}
