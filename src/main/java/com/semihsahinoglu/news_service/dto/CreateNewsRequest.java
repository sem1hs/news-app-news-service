package com.semihsahinoglu.news_service.dto;

import com.semihsahinoglu.news_service.entity.NewsCategory;

import java.util.List;

public record CreateNewsRequest(
        String title,
        String slug,
        String content,
        String spot,
        NewsCategory category,
        String subCategory,
        List<String> tags,
        String imageUrl,
        Integer views
) {
}
