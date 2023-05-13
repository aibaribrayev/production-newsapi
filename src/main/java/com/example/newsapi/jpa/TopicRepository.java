package com.example.newsapi.jpa;

import com.example.newsapi.models.Source;
import com.example.newsapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}