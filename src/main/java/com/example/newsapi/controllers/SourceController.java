package com.example.newsapi.controllers;

import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import com.example.newsapi.services.NewsServiceImpl;
import com.example.newsapi.services.SourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/source")
public class SourceController {

    private final SourceServiceImpl sourceService;

    public SourceController(SourceServiceImpl sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping("")
    public List<Source> getAllSources() {
        return sourceService.getAllSources();
    }

    @PostMapping("")
    public Source addSource(@RequestBody Source source) {
        return sourceService.addSource(source);
    }
}
