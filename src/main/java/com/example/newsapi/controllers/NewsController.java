package com.example.newsapi.controllers;

import com.example.newsapi.dtos.AddNewsRequest;
import com.example.newsapi.models.News;
import com.example.newsapi.services.NewsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsServiceImpl newsService;

    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @GetMapping("")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping("")
    public ResponseEntity<News> addNews(@RequestBody AddNewsRequest requestDTO) {

        News savedNews = newsService.addNews(requestDTO);

        if (savedNews == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNews.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}