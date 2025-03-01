package com.alten.shop.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.alten.shop.controller";

    @Bean
    public org.springdoc.core.models.GroupedOpenApi groupedOpenApi() {
        return org.springdoc.core.models.GroupedOpenApi.builder()
                .group("All")
                .packagesToScan(BASE_PACKAGE)
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("Module name API")
                .description("Service that exposes REST API to access data.")
                .version("1.0.0"));
    }
}
