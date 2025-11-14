package com.springai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenAiConfig {

    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    @Bean
    OpenAiApi openAiApi() {
        log.debug("OpenAi API 클라이언트 초기화");
        return OpenAiApi.builder()
                .apiKey(OPEN_AI_KEY)
                .build();
    }
}
