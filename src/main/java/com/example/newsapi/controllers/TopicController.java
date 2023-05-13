package com.example.newsapi.controllers;
import com.example.newsapi.dtos.AddSourceDTO;
import com.example.newsapi.models.Source;
import com.example.newsapi.models.Topic;
import com.example.newsapi.services.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Topic> addTopic(@RequestBody AddSourceDTO requestDTO) {
        Topic savedTopic = topicService.addTopic(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTopic.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
