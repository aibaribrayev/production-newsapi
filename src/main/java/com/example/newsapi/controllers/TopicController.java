package com.example.newsapi.controllers;
import com.example.newsapi.models.Topic;
import com.example.newsapi.services.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicServiceImpl topicService;

    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping("")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("")
    public Topic addTopic(@RequestBody Topic topic) {
        return topicService.addTopic(topic);
    }
}
