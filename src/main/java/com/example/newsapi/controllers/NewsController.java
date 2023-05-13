package com.example.newsapi.controllers;

import com.example.newsapi.models.News;
import com.example.newsapi.services.NewsServiceImpl;
import org.springframework.web.bind.annotation.*;


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
    public News addNews(@RequestBody News news) {
        return newsService.addNews(news);
    }

}