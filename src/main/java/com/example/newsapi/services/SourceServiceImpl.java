package com.example.newsapi.services;

import com.example.newsapi.dtos.AddSourceDto;
import com.example.newsapi.errors.SourceNotFoundException;
import com.example.newsapi.jpa.SourceRepository;

import com.example.newsapi.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Source getSourceById(Long id) {
        Optional<Source> source = sourceRepository.findById(id);

        if(source.isEmpty())
            throw new SourceNotFoundException("can't find source with id:"+id);

        return source.get();
    }

    @Override
    public Source addSource(AddSourceDto requestDTO) {
        Source savedSource = new Source(requestDTO.getName());
        return sourceRepository.save(savedSource);
    }

    @Override
    public Source updateSource(Long id, AddSourceDto requestDTO) {
        Optional<Source> currnetSource = sourceRepository.findById(id);

        if(currnetSource.isEmpty())
            throw new SourceNotFoundException("can't find source with id:"+id);

        Source updatedSource = currnetSource.get();
        updatedSource.setName(requestDTO.getName());
        return sourceRepository.save(updatedSource);
    }
    @Override
    public void deleteSource(Long id) {
        Optional<Source> sourceOptional = sourceRepository.findById(id);

        if (sourceOptional.isEmpty()) {
            throw new SourceNotFoundException("can't find source with id:"+id);
        }

        Source source = sourceOptional.get();
        sourceRepository.delete(source);
    }
}
