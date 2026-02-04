package com.semihsahinoglu.news_service.controller;

import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.service.NewsSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news/search")
public class NewsSearchController {

    private final NewsSearchService newsSearchService;

    public NewsSearchController(NewsSearchService newsSearchService) {
        this.newsSearchService = newsSearchService;
    }

    @GetMapping
    public ResponseEntity<Page<NewsResponse>> search(@RequestParam String q, Pageable pageable) {
        Page<NewsResponse> newsResponses = newsSearchService.search(q, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(newsResponses);
    }
}
