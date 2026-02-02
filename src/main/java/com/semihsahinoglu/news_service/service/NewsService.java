package com.semihsahinoglu.news_service.service;

import com.semihsahinoglu.news_service.client.LeagueClient;
import com.semihsahinoglu.news_service.client.TeamClient;
import com.semihsahinoglu.news_service.dto.*;
import com.semihsahinoglu.news_service.entity.News;
import com.semihsahinoglu.news_service.entity.NewsCategory;
import com.semihsahinoglu.news_service.exception.NewsNotFoundException;
import com.semihsahinoglu.news_service.mapper.NewsMapper;
import com.semihsahinoglu.news_service.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final LeagueClient leagueClient;
    private final TeamClient teamClient;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, LeagueClient leagueClient, TeamClient teamClient) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.leagueClient = leagueClient;
        this.teamClient = teamClient;
    }

    public NewsResponse create(CreateNewsRequest createNewsRequest) {
        News news = newsMapper.toEntity(createNewsRequest);
        enrichWithLeagueAndTeam(news);
        News saved = newsRepository.save(news);
        return newsMapper.toDto(saved);
    }

    public Page<NewsResponse> getAll(NewsFilterRequest newsFilterRequest, Pageable pageable) {
        Page<News> newsList;

        if (newsFilterRequest.leagueName().isPresent()) {
            newsList = newsRepository.findByLeagueName(newsFilterRequest.leagueName().get(), pageable);
        } else {
            newsList = newsRepository.findAll(pageable);
        }
        return newsList.map(newsMapper::toDto);
    }

    public NewsResponse getById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Haber bulunamadı !"));
        return newsMapper.toDto(news);
    }

    public NewsResponse getBySlug(String slug) {
        News news = newsRepository.findNewsBySlug(slug).orElseThrow(() -> new NewsNotFoundException("Haber bulunamadı !"));
        newsRepository.incrementViews(news.getId());
        return newsMapper.toDto(news);
    }

    public List<NewsResponse> getBreakingNews() {
        List<News> newsResponses = newsRepository.findTop10ByIsBreakingTrueOrderByCreatedDateDesc();
        return newsResponses.stream().map(newsMapper::toDto).toList();
    }

    public List<NewsResponse> getPopularNews(Integer dayRange) {
        List<News> newsList = newsRepository.findPopularNews(LocalDateTime.now().minusDays(dayRange), PageRequest.of(0, 8));
        return newsList.stream().map(newsMapper::toDto).toList();
    }

    public List<NewsResponse> getLatestNews(NewsCategory category) {
        Pageable pageable = PageRequest.of(0, 5);
        List<News> newsList = newsRepository.findLatestByCategory(category, pageable);
        return newsList.stream().map(newsMapper::toDto).toList();
    }

    public NewsResponse update(Long newsId, UpdateNewsRequest newsRequest) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException("Haber bulunamadı !"));

        Long oldLeagueId = news.getLeagueId();
        Long oldTeamId = news.getTeamId();

        newsMapper.updateEntity(news, newsRequest);

        boolean leagueChanged = newsRequest.leagueId().isPresent() && !newsRequest.leagueId().get().equals(oldLeagueId);
        boolean teamChanged = newsRequest.teamId().isPresent() && !newsRequest.teamId().get().equals(oldTeamId);

        if (leagueChanged || teamChanged) enrichWithLeagueAndTeam(news);

        News updatedNews = newsRepository.save(news);
        return newsMapper.toDto(updatedNews);
    }

    public void delete(Long newsId) {
        int deletedCount = newsRepository.deleteNewsById(newsId);
        if (deletedCount == 0) {
            throw new NewsNotFoundException("Haber bulunamadı !");
        }
    }


    private void enrichWithLeagueAndTeam(News news) {
        CompletableFuture<LeagueResponse> leagueFuture = CompletableFuture.supplyAsync(() -> leagueClient.findLeagueById(news.getLeagueId()));

        CompletableFuture<TeamResponse> teamFuture = news.getTeamId() != null ? CompletableFuture.supplyAsync(() -> teamClient.findTeamById(news.getTeamId())) : CompletableFuture.completedFuture(null);

        LeagueResponse league = leagueFuture.join();
        TeamResponse team = teamFuture.join();

        news.setLeagueName(league.name());

        if (team != null) {
            news.setTeamName(team.name());
        }
    }

}
