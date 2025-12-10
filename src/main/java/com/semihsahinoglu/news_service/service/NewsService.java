package com.semihsahinoglu.news_service.service;

import com.semihsahinoglu.news_service.dto.CreateNewsRequest;
import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.dto.UpdateNewsRequest;
import com.semihsahinoglu.news_service.entity.News;
import com.semihsahinoglu.news_service.exception.NewsNotFoundException;
import com.semihsahinoglu.news_service.mapper.NewsMapper;
import com.semihsahinoglu.news_service.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    public NewsResponse create(CreateNewsRequest createNewsRequest) {
        News news = newsMapper.toEntity(createNewsRequest);
        News saved = newsRepository.save(news);
        return newsMapper.toDto(saved);
    }

    public Page<NewsResponse> getAll(Pageable pageable) {
        Page<News> newsList = newsRepository.findAll(pageable);
        return newsList.map(newsMapper::toDto);
    }

    public NewsResponse update(Long newsId, UpdateNewsRequest newsRequest) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException("Haber bulunamadı !"));
        newsMapper.updateEntity(news, newsRequest);
        News updatedNews = newsRepository.save(news);
        return newsMapper.toDto(updatedNews);
    }

    public void delete(Long newsId) {
        int deletedCount = newsRepository.deleteNewsById(newsId);
        if (deletedCount == 0) {
            throw new NewsNotFoundException("Haber bulunamadı !");
        }
    }
}
