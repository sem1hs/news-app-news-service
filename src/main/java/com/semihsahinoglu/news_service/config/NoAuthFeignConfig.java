package com.semihsahinoglu.news_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoAuthFeignConfig {

    @Bean
    public RequestInterceptor serviceNameInterceptor() {
        return template -> template.header("X-Service-Name", "news-service");
    }
}