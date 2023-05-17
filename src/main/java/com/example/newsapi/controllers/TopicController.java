package com.example.newsapi.controllers;
import com.example.newsapi.dtos.AddTopicRequest;
import com.example.newsapi.models.Topic;
import com.example.newsapi.services.TopicServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping("/{id}")
    public EntityModel<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);

        EntityModel<Topic> entityModel = EntityModel.of(topic);
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllTopics());
        entityModel.add(link.withRel("all-topics"));

        return entityModel;
    }

    @PostMapping("")
    public ResponseEntity<Topic> addTopic(@RequestBody AddTopicRequest requestDTO) {
        Topic savedTopic = topicService.addTopic(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTopic.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTopic(@PathVariable Long id, @RequestBody AddTopicRequest requestDTO){
        Topic topic = topicService.updateTopic(id, requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topic.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }


}
