package com.semihsahinoglu.news_service.repository.specification;


import com.semihsahinoglu.news_service.entity.News;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecification {

    private NewsSpecification() {
    }

    public static Specification<News> simpleSearch(String keyword) {
        return (root, _, cb) -> {

            String likePattern = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), likePattern),
                    cb.like(cb.lower(root.get("spot")), likePattern),
                    cb.like(cb.lower(root.get("teamName")), likePattern),
                    cb.like(cb.lower(root.get("leagueName")), likePattern)
            );
        };
    }

}