package com.semihsahinoglu.news_service.controller;

import com.semihsahinoglu.news_service.dto.CreateNewsRequest;
import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.dto.UpdateNewsRequest;
import com.semihsahinoglu.news_service.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<NewsResponse>> getAll(Pageable pageable) {
        Page<NewsResponse> newsResponses = newsService.getAll(pageable);
        return ResponseEntity.ok().body(newsResponses);
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
