package com.semihsahinoglu.news_service.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "news")
public class News extends Auditable {

    @Column(nullable = false, length = 300)
    private String title;

    // SEO için URL slug
    @Column
    private String slug;

    @Lob
    @Column(nullable = false)
    private String content;

    // Spot - kısa özet
    @Column(nullable = false, length = 400)
    private String spot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsCategory category;

    private String subCategory;

    @ElementCollection
    private List<String> tags;

    @Column(nullable = false)
    private String imageUrl;

    private Integer views;

    public News() {
    }

    public News(String title, String slug, String content, String spot, NewsCategory category, String subCategory, List<String> tags, String imageUrl, Integer views) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.spot = spot;
        this.category = category;
        this.subCategory = subCategory;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.views = views;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String slug;
        private String content;
        private String spot;
        private NewsCategory category;
        private String subCategory;
        private List<String> tags;
        private String imageUrl;
        private Integer views;

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

        public News build() {
            return new News(title, slug, content, spot, category, subCategory, tags, imageUrl, views);
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
