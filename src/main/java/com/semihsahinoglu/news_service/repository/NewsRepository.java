package com.semihsahinoglu.news_service.repository;

import com.semihsahinoglu.news_service.entity.News;
import com.semihsahinoglu.news_service.entity.NewsCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Modifying
    @Query("DELETE FROM News n WHERE n.id = :id")
    int deleteNewsById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE News n SET n.views = n.views + 1 WHERE n.id = :id")
    void incrementViews(@Param("id") Long id);

    @Query("SELECT n FROM News n WHERE n.createdDate >= :after ORDER BY n.views DESC")
    List<News> findPopularNews(@Param("after") LocalDateTime after, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.category = :category ORDER BY n.createdDate DESC")
    List<News> findLatestByCategory(@Param("category") NewsCategory category, Pageable pageable);

    Optional<News> findNewsBySlug(String slug);

    Page<News> findByLeagueName(String leagueName, Pageable pageable);

    List<News> findTop10ByIsBreakingTrueOrderByCreatedDateDesc();

}
