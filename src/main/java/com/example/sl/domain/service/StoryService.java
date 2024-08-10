package com.example.sl.domain.service;


import com.example.sl.controller.exception.ResourceNotFoundException;
import com.example.sl.entity.StoryEntity;

import com.example.sl.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    private final Path root = Paths.get("src/main/resources/static/images");

    public List<StoryEntity> findAll() {
        return storyRepository.findAll();
    }

    public Optional<StoryEntity> findById(Long id) {
        return storyRepository.findById(id);
    }

    public StoryEntity save(String title, String author, String content, MultipartFile image) {
        StoryEntity story = new StoryEntity();
        story.setTitle(title);
        story.setAuthor(author);
        story.setContent(content);
        story.setImage(storeImage(image));
        return storyRepository.save(story);
    }

    public StoryEntity update(Long id, String title, String author, String content, MultipartFile image) {
        StoryEntity story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        story.setTitle(title);
        story.setAuthor(author);
        story.setContent(content);
        if (!image.isEmpty()) {
            story.setImage(storeImage(image));
        }
        return storyRepository.save(story);
    }

    public void delete(Long id) {
        if (!storyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Story not found");
        }
        storyRepository.deleteById(id);
    }

    private String storeImage(MultipartFile image) {
        try {
            Files.copy(image.getInputStream(), this.root.resolve(image.getOriginalFilename()));
            return image.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }
    }
}
