package com.example.newsapi.controllers;

import com.example.newsapi.dtos.AddSourceDto;
import com.example.newsapi.models.Source;
import com.example.newsapi.services.SourceServiceImpl;
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

    @GetMapping("/{id}")
    public EntityModel<Source> getSourceById(@PathVariable Long id) {
        Source source = sourceService.getSourceById(id);

        EntityModel<Source> entityModel = EntityModel.of(source);
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllSources());
        entityModel.add(link.withRel("all-sources"));

        return entityModel;
    }
    @PostMapping("")
    public ResponseEntity<Source> addSource(@RequestBody AddSourceDto requestDTO) {
        Source savedSource = sourceService.addSource(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSource.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSource(@PathVariable Long id, @RequestBody AddSourceDto requestDTO){
        Source source = sourceService.updateSource(id, requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(source.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        sourceService.deleteSource(id);
        return ResponseEntity.noContent().build();
    }
}
