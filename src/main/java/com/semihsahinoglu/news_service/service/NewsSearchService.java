package com.semihsahinoglu.news_service.service;

import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.entity.News;
import com.semihsahinoglu.news_service.mapper.NewsMapper;
import com.semihsahinoglu.news_service.repository.NewsRepository;
import com.semihsahinoglu.news_service.repository.specification.NewsSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class NewsSearchService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsSearchService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    public Page<NewsResponse> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) return Page.empty(pageable);

        Page<News> newsList = newsRepository.findAll(NewsSpecification.simpleSearch(keyword), pageable);
        return newsList.map(newsMapper::toDto);
    }
}
