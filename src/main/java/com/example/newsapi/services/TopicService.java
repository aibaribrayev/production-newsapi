package com.example.newsapi.services;
import com.example.newsapi.dtos.AddTopicRequest;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {
    public List<Topic> getAllTopics();

    public Topic addTopic(AddTopicRequest topic);

    public Topic getTopicById(Long id);

    public Topic updateTopic(Long id, AddTopicRequest requestDTO);

    public void deleteTopic(Long id);
}