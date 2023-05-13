package com.example.newsapi.services;
import com.example.newsapi.dtos.AddTopicDTO;
import com.example.newsapi.errors.TopicNotFoundException;
import com.example.newsapi.jpa.TopicRepository;
import com.example.newsapi.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Topic addTopic(AddTopicDTO requestDTO) {
        Topic savedTopic = new Topic(requestDTO.getName());
        return topicRepository.save(savedTopic);
    }

    @Override
    public Topic getTopicById(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);

        if(topic.isEmpty())
            throw new TopicNotFoundException("can't find topic with id:"+id);

        return topic.get();
    }

    @Override
    public Topic updateTopic(Long id, AddTopicDTO requestDTO) {
        Optional<Topic> currentTopic = topicRepository.findById(id);
        if(currentTopic.isEmpty())
            throw new TopicNotFoundException("can't find topic with id:"+id);

        Topic updatedTopic = currentTopic.get();
        updatedTopic.setName(requestDTO.getName());
        return topicRepository.save(updatedTopic);
    }

    @Override
    public void deleteTopic(Long id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);

        if (topicOptional.isEmpty()) {
            throw new TopicNotFoundException("can't find topic with id:"+id);
        }

        Topic topic = topicOptional.get();
        topicRepository.delete(topic);
    }
}
