package com.semihsahinoglu.news_service.dto;

import com.semihsahinoglu.news_service.entity.NewsCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record NewsResponse(
        Long id,
        String title,
        String slug,
        String content,
        String spot,
        NewsCategory category,
        String subCategory,
        List<String> tags,
        String imageUrl,
        Integer views,
        String createdBy,
        LocalDateTime createdDate
) implements ApiResponse {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String slug;
        private String content;
        private String spot;
        private NewsCategory category;
        private String subCategory;
        private List<String> tags;
        private String imageUrl;
        private Integer views;
        private String createdBy;
        private LocalDateTime createdDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder spot(String spot) {
            this.spot = spot;
            return this;
        }

        public Builder category(NewsCategory category) {
            this.category = category;
            return this;
        }

        public Builder subCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder views(Integer views) {
            this.views = views;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public NewsResponse build() {
            return new NewsResponse(id, title, slug, content, spot, category, subCategory, tags, imageUrl, views, createdBy, createdDate);
        }

    }
}
