package com.etiqa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("ETIQA_TEST-API-DOC").version("1.0.0-SNAPSHOT").description("REST API for a managing Customers and Products."));
    }
    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder().group("http").pathsToMatch("/**").build();
    }

}
