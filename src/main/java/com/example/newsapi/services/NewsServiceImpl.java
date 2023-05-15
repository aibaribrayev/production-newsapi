package com.example.newsapi.services;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.errors.SourceNotFoundException;
import com.example.newsapi.errors.TopicNotFoundException;
import com.example.newsapi.jpa.NewsRepository;
import com.example.newsapi.jpa.SourceRepository;
import com.example.newsapi.jpa.TopicRepository;
import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import com.example.newsapi.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }
    @Override
    public News addNews(AddNewsRequest requestDTO) {
        Optional<Source> source = sourceRepository.findById(requestDTO.getSourceId());
        Optional<Topic> topic = topicRepository.findById(requestDTO.getTopicId());

        if (source.isEmpty()) {
            throw new SourceNotFoundException("can't find source with id:"+requestDTO.getSourceId());
        }

        if (topic.isEmpty()) {
            throw new TopicNotFoundException("can't find source with id:"+requestDTO.getSourceId());
        }

        News news = new News(requestDTO.getTitle(), requestDTO.getDescription(), source.get(), topic.get());
        return newsRepository.save(news);
    }

    @Override
    public News getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);

        if(news.isEmpty())
            throw new SourceNotFoundException("can't find news with id:"+id);

        return news.get();
    }

    @Override
    public News updateNews(Long id, AddNewsRequest requestDTO) {

        Optional<News> currnetNews = newsRepository.findById(id);

        Optional<Source> source = sourceRepository.findById(requestDTO.getSourceId());

        Optional<Topic> topic = topicRepository.findById(requestDTO.getTopicId());

        if(currnetNews.isEmpty())
            throw new SourceNotFoundException("can't find news with id:"+id);

        if (source.isEmpty()) {
            throw new SourceNotFoundException("can't find source with id:"+requestDTO.getSourceId());
        }

        if (topic.isEmpty()) {
            throw new TopicNotFoundException("can't find source with id:"+requestDTO.getSourceId());
        }


        News updatedNews = currnetNews.get();

        updatedNews.setTitle(requestDTO.getTitle());
        updatedNews.setDescription(requestDTO.getDescription());
        updatedNews.setSource(source.get());
        updatedNews.setTopic(topic.get());

        return newsRepository.save(updatedNews);
    }
    @Override
    public void deleteNews(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isEmpty()) {
            throw new SourceNotFoundException("can't find news with id:"+id);
        }

        News news = newsOptional.get();
        newsRepository.delete(news);
    }

    public Page<News> getNewsBySource(Long sourceId, Pageable pageable) {
        Optional<Source> source = sourceRepository.findById(sourceId);

        if(source.isEmpty())
            throw new SourceNotFoundException("can't find source with id:"+sourceId);

        return newsRepository.findBySourceId(source.get().getId(), pageable);
    }

    public Page<News> getNewsByTopic(Long topicId, Pageable pageable) {
        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isEmpty())
            throw new TopicNotFoundException("can't find topic with id:"+topicId);

        return newsRepository.findByTopicId(topic.get().getId(), pageable);
    }
}
