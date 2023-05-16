package com.example.newsapi.services;
import com.example.newsapi.dtos.AddTopicDto;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {
    public List<Topic> getAllTopics();

    public Topic addTopic(AddTopicDto topic);

    public Topic getTopicById(Long id);

    public Topic updateTopic(Long id, AddTopicDto requestDTO);

    public void deleteTopic(Long id);
}