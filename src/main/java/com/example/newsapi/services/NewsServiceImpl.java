package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.jpa.NewsRepository;
import com.example.newsapi.jpa.SourceRepository;
import com.example.newsapi.jpa.TopicRepository;
import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final TopicRepository topicRepository;

    public NewsServiceImpl(NewsRepository newsRepository, SourceRepository sourceRepository, TopicRepository topicRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News addNews(AddNewsRequest requestDTO) {
        Optional<Source> source = sourceRepository.findById(requestDTO.getSourceId());
        Optional<Topic> topic = topicRepository.findById(requestDTO.getTopicId());

        if (source.isEmpty() || topic.isEmpty()) {
            return null;
        }

        News news = new News(requestDTO.getTitle(), requestDTO.getDescription(), source.get(), topic.get());
        return newsRepository.save(news);
    }
}
