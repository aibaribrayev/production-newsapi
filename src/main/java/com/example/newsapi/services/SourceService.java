package com.example.newsapi.services;

import com.example.newsapi.dtos.AddSourceDTO;
import com.example.newsapi.models.News;
import com.example.newsapi.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SourceService {
    public List<Source> getAllSources();
    public Source getSourceById(Long id);
    public Source addSource(AddSourceDTO source);

    public Source updateSource(Long id, AddSourceDTO updatedSource);
    public void deleteSource(Long id);
}
