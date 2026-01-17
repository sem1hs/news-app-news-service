package com.semihsahinoglu.news_service.repository;

import com.semihsahinoglu.news_service.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Modifying
    @Query("DELETE FROM News n WHERE n.id = :id")
    int deleteNewsById(@Param("id") Long id);

    Optional<News> findNewsBySlug(String slug);

    Page<News> findByLeagueName(String leagueName, Pageable pageable);
}
