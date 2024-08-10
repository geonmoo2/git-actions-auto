package com.example.sl.domain.dto;

import com.example.sl.entity.ImageEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String name;
    private String title;
    private byte[] data;
    private String extension;

    private String image64Based;
    public static ImageDto toDto(ImageEntity entity) {
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTitle(entity.getTitle());
        dto.setData(entity.getData());
        dto.setExtension(entity.getExtension());
        return dto;
    }

}