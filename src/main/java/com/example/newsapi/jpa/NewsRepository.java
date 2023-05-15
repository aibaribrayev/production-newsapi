package com.example.newsapi.jpa;

import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAll(Pageable pageable);
    @Query("SELECT n FROM News n WHERE n.source.id = :sourceId")
    Page<News> findBySourceId(@Param("sourceId") Long sourceId, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.topic.id = :topicId")
    Page<News> findByTopicId(@Param("topicId") Long topicId, Pageable pageable);

    Long countBySource(Source source);
}

