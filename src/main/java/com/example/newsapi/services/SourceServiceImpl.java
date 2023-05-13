package com.example.newsapi.services;

import com.example.newsapi.jpa.SourceRepository;

import com.example.newsapi.models.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {
    private final SourceRepository sourceRepository;

    public SourceServiceImpl(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public List<Source> getAllSources() {
        return sourceRepository.findAll();
    }
    @Override
    public Source addSource(Source source) {
        return sourceRepository.save(source);
    }
}
