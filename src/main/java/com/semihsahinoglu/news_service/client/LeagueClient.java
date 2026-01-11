package com.semihsahinoglu.news_service.client;

import com.semihsahinoglu.news_service.config.InternalFeignConfig;
import com.semihsahinoglu.news_service.dto.LeagueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "league-service",configuration = InternalFeignConfig.class)
public interface LeagueClient {

    @GetMapping("/internal/league/{id}/exists")
    Boolean existsById(@PathVariable("id") Long id);

    @GetMapping("/internal/league/{id}")
    LeagueResponse findLeagueById(@PathVariable("id") Long id);
}
