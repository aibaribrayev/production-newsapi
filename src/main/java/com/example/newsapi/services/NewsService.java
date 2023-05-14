package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;

import com.example.newsapi.models.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    public Page<News> getAllNews(Pageable pageable);
    public News addNews(AddNewsRequest requestDTO);

    public News getNewsById(Long id);;
    public News updateNews(Long id, AddNewsRequest requestDTO);
    public void deleteNews(Long id);

    public Page<News> getNewsBySource(Long sourceId, Pageable pageable);
    public Page<News> getNewsByTopic(Long topicId, Pageable pageable);

}
