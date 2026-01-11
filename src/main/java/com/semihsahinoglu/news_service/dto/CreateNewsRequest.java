package com.semihsahinoglu.news_service.dto;

import com.semihsahinoglu.news_service.entity.NewsCategory;

import java.util.List;
import java.util.Optional;

public record CreateNewsRequest(
        String title,
        String slug,
        String content,
        String spot,
        NewsCategory category,
        List<String> tags,
        String imageUrl,
        Long leagueId,
        Optional<Long> teamId
) {
}
