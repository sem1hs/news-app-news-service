package com.semihsahinoglu.news_service.mapper;

import com.semihsahinoglu.news_service.dto.CreateNewsRequest;
import com.semihsahinoglu.news_service.dto.NewsResponse;
import com.semihsahinoglu.news_service.dto.UpdateNewsRequest;
import com.semihsahinoglu.news_service.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public NewsResponse toDto(News news) {
        if (news == null) return null;

        NewsResponse newsResponse = NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .slug(news.getSlug())
                .content(news.getContent())
                .spot(news.getSpot())
                .category(news.getCategory())
                .subCategory(news.getSubCategory())
                .tags(news.getTags())
                .imageUrl(news.getImageUrl())
                .views(news.getViews())
                .createdBy(news.getCreatedBy())
                .createdDate(news.getCreatedDate())
                .build();

        return newsResponse;
    }

    public News toEntity(CreateNewsRequest createNewsRequest) {
        if (createNewsRequest == null) return null;

        News news = News.builder()
                .title(createNewsRequest.title())
                .slug(createNewsRequest.slug())
                .content(createNewsRequest.content())
                .spot(createNewsRequest.spot())
                .category(createNewsRequest.category())
                .subCategory(createNewsRequest.subCategory())
                .tags(createNewsRequest.tags())
                .imageUrl(createNewsRequest.imageUrl())
                .views(createNewsRequest.views())
                .build();

        return news;
    }

    public void updateEntity(News news, UpdateNewsRequest newsRequest) {

        newsRequest.title().ifPresent(news::setTitle);
        newsRequest.slug().ifPresent(news::setSlug);
        newsRequest.content().ifPresent(news::setContent);
        newsRequest.spot().ifPresent(news::setSpot);
        newsRequest.category().ifPresent(news::setCategory);
        newsRequest.subCategory().ifPresent(news::setSubCategory);
        newsRequest.tags().ifPresent(news::setTags);
        newsRequest.imageUrl().ifPresent(news::setImageUrl);
        newsRequest.views().ifPresent(news::setViews);

    }
}
