package com.semihsahinoglu.news_service.client;

import com.semihsahinoglu.news_service.config.NoAuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", configuration = NoAuthFeignConfig.class)
public interface AuthClient {

    @PostMapping("/internal/auth/service-token")
    String getServiceToken();
}
