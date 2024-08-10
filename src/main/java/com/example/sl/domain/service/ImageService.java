package com.example.sl.domain.service;

import com.example.sl.domain.dto.FaqBoardDTO;
import com.example.sl.domain.dto.ImageDto;
import com.example.sl.entity.ImageEntity;
import com.example.sl.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<ImageEntity> getAllImages(int page, int pageSize) {
        int offset = page * pageSize;
        return imageRepository.findAllWithPagination(offset, pageSize);
    }
    public List<ImageDto> findAll() {
        List<ImageEntity> imageEntities = imageRepository.findAll();
        return imageEntities.stream()
                .map(ImageDto::toDto) // ImageDto의 정적 메서드를 참조
                .collect(Collectors.toList());
    }



    public ImageEntity getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public void saveImage(ImageEntity image) {
        imageRepository.save(image);
    }

    public List<ImageEntity> searchImagesByName(String name) {
        return imageRepository.findByNameContaining(name);
    }

    public long countImages() {
        return imageRepository.count();
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }
}