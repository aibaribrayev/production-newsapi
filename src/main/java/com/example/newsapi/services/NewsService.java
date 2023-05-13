package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.models.News;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    public List<News> getAllNews();
    public News addNews(AddNewsRequest requestDTO);
}
