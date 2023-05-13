package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.jpa.NewsRepository;
import com.example.newsapi.jpa.SourceRepository;
import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import com.example.newsapi.services.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;

    public NewsServiceImpl(NewsRepository newsRepository, SourceRepository sourceRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News addNews(AddNewsRequest requestDTO) {
        Optional<Source> source = sourceRepository.findById(requestDTO.getSourceId());
        if (source.isEmpty()) {
            return null;
        }
        News news = new News(requestDTO.getTitle(), requestDTO.getDescription(), source.get(), requestDTO.getSourceId());
        return newsRepository.save(news);
    }
}
