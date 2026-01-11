package com.semihsahinoglu.news_service.dto;

import com.semihsahinoglu.news_service.entity.NewsCategory;

import java.util.List;
import java.util.Optional;

public record UpdateNewsRequest(
        Optional<String> title,
        Optional<String> slug,
        Optional<String> content,
        Optional<String> spot,
        Optional<NewsCategory> category,
        Optional<List<String>> tags,
        Optional<String> imageUrl,
        Optional<Integer> views,
        Optional<Long> leagueId,
        Optional<Long> teamId
) {
}
