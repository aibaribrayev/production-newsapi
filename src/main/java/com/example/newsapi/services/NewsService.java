package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;

import com.example.newsapi.models.News;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    public List<News> getAllNews();
    public News addNews(AddNewsRequest requestDTO);

    public News getNewsById(Long id);;
    public News updateNews(Long id, AddNewsRequest requestDTO);
    public void deleteNews(Long id);
}
