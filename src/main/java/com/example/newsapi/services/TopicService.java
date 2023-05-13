package com.example.newsapi.services;

import com.example.newsapi.models.Source;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {
    public List<Topic> getAllTopics();
    public Topic addTopic(Topic topic);
}
