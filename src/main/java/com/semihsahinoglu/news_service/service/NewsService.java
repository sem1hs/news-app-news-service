package com.semihsahinoglu.news_service.service;

import com.semihsahinoglu.news_service.client.LeagueClient;
import com.semihsahinoglu.news_service.client.TeamClient;
import com.semihsahinoglu.news_service.dto.*;
import com.semihsahinoglu.news_service.entity.News;
import com.semihsahinoglu.news_service.exception.NewsNotFoundException;
import com.semihsahinoglu.news_service.mapper.NewsMapper;
import com.semihsahinoglu.news_service.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<NewsResponse> getAll(Pageable pageable) {
        Page<News> newsList = newsRepository.findAll(pageable);
        return newsList.map(newsMapper::toDto);
    }

    public NewsResponse getById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Haber bulunamadı !"));
        return newsMapper.toDto(news);
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
