package com.example.observation.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("Observation API")
                .version("1.0")
                .description("This API exposes endpoints to view observations.");
        return new OpenAPI().info(info);
    }
}
