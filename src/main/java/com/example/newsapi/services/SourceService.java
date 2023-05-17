package com.example.newsapi.services;

import com.example.newsapi.dtos.AddSourceRequest;
import com.example.newsapi.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SourceService {
    public List<Source> getAllSources();
    public Source getSourceById(Long id);
    public Source addSource(AddSourceRequest source);
    public Source updateSource(Long id, AddSourceRequest updatedSource);
    public void deleteSource(Long id);
}
