package com.semihsahinoglu.news_service.controller;

import com.semihsahinoglu.news_service.dto.CreateNewsRequest;
import com.semihsahinoglu.news_service.dto.NewsFilterRequest;
import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.dto.UpdateNewsRequest;
import com.semihsahinoglu.news_service.entity.NewsCategory;
import com.semihsahinoglu.news_service.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@Valid @RequestBody CreateNewsRequest createNewsRequest) {
        NewsResponse newsResponse = newsService.create(createNewsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsResponse);
    }

    @GetMapping
    public ResponseEntity<Page<NewsResponse>> getAll(@ModelAttribute NewsFilterRequest newsFilterRequest, Pageable pageable) {
        Page<NewsResponse> newsResponses = newsService.getAll(newsFilterRequest, pageable);
        return ResponseEntity.ok().body(newsResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getById(@PathVariable Long id) {
        NewsResponse newsResponse = newsService.getById(id);
        return ResponseEntity.ok().body(newsResponse);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<NewsResponse> getBySlug(@PathVariable String slug) {
        NewsResponse newsResponse = newsService.getBySlug(slug);
        return ResponseEntity.ok().body(newsResponse);
    }

    @GetMapping("/breaking")
    public ResponseEntity<List<NewsResponse>> getBreakingNews() {
        List<NewsResponse> newsResponses = newsService.getBreakingNews();
        return ResponseEntity.status(HttpStatus.OK).body(newsResponses);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<NewsResponse>> getPopularNews(@RequestParam Integer dayRange) {
        List<NewsResponse> newsResponses = newsService.getPopularNews(dayRange);
        return ResponseEntity.status(HttpStatus.OK).body(newsResponses);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsResponse>> getLatestNews(@RequestParam NewsCategory category) {
        List<NewsResponse> newsResponses = newsService.getLatestNews(category);
        return ResponseEntity.status(HttpStatus.OK).body(newsResponses);
    }

    @PatchMapping("/{id}")
    ResponseEntity<NewsResponse> update(@PathVariable Long id, @RequestBody UpdateNewsRequest newsRequest) {
        NewsResponse newsResponse = newsService.update(id, newsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(newsResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
