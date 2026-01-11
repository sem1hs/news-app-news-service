package com.semihsahinoglu.news_service.client;

import com.semihsahinoglu.news_service.config.InternalFeignConfig;
import com.semihsahinoglu.news_service.dto.LeagueResponse;
import com.semihsahinoglu.news_service.dto.TeamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teams-service",configuration = InternalFeignConfig.class)
public interface TeamClient {

    @GetMapping("/internal/teams/{id}/exists")
    Boolean existsById(@PathVariable("id") Long id);

    @GetMapping("/internal/teams/{id}")
    TeamResponse findTeamById(@PathVariable("id") Long id);
}
