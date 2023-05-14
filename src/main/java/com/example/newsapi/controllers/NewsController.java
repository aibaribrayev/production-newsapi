package com.example.newsapi.controllers;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.models.News;
import com.example.newsapi.services.NewsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/news")
public class NewsController {
    private final NewsServiceImpl newsService;

    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @PostMapping("")
    public ResponseEntity<News> addNews(@RequestBody AddNewsRequest requestDTO) {

        News savedNews = newsService.addNews(requestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNews.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public EntityModel<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);
        EntityModel<News> entityModel = EntityModel.of(news);
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllNews(0, 20));
        entityModel.add(link.withRel("all-news"));

        return entityModel;
    }

    @GetMapping("")
    public ResponseEntity<Page<News>> getAllNews(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsService.getAllNews(pageable);
        return ResponseEntity.ok(newsPage);
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<Page<News>> getNewsBySourceId(@PathVariable Long sourceId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsService.getNewsBySource(sourceId, pageable);
        return ResponseEntity.ok(newsPage);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<Page<News>> getNewsByTopicId(@PathVariable Long topicId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsService.getNewsByTopic(topicId, pageable);
        return ResponseEntity.ok(newsPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNews(@PathVariable Long id, @RequestBody AddNewsRequest requestDTO){
        News news = newsService.updateNews(id, requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(news.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}