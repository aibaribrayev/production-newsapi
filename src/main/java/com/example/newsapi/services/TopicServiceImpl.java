package com.example.newsapi.services;

import com.example.newsapi.jpa.TopicRepository;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}
