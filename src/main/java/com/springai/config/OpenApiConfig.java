package com.springai.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title="Spring AI Tutorial API", version="1.0", description = "Spring AI를 활용한 챗봇 API")
)
public class OpenApiConfig {
}

