package com.example.newsapi.services;

import com.example.newsapi.dtos.AddSourceDto;
import com.example.newsapi.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SourceService {
    public List<Source> getAllSources();
    public Source getSourceById(Long id);
    public Source addSource(AddSourceDto source);

    public Source updateSource(Long id, AddSourceDto updatedSource);
    public void deleteSource(Long id);
}
