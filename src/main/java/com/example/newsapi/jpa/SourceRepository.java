package com.example.newsapi.jpa;

import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long>{
}
