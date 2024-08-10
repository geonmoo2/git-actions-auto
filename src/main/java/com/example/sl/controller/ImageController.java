package com.example.sl.controller;

import com.example.sl.domain.service.ImageService;
import com.example.sl.entity.ImageEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/fan/fan_gallery")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String viewGallery(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        int pageSize = 16;
        List<ImageEntity> images = imageService.getAllImages(page, pageSize);
        long totalImages = imageService.countImages();
        int totalPages = (int) Math.ceil((double) totalImages / pageSize);
        model.addAttribute("images", images);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "fan/fan_gallery";
    }

    @PostMapping("/add")
    public String addImage(@RequestParam("name") String name,
                           @RequestParam("title") String title,
                           @RequestParam("file") MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setName(name);
        image.setTitle(title);
        image.setData(file.getBytes());

        // 파일 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        image.setExtension(extension);

        imageService.saveImage(image);
        return "redirect:/fan/fan_gallery";
    }

    @GetMapping("/search")
    public String searchImages(Model model, @RequestParam("name") String name) {
        List<ImageEntity> images = imageService.searchImagesByName(name);
        model.addAttribute("images", images);
        return "fan/fan_gallery";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        ImageEntity image = imageService.getImageById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getMediaType(image.getExtension()));
        headers.setContentLength(image.getData().length);
        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }

    private MediaType getMediaType(String extension) {
        switch (extension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("id") Long id) throws UnsupportedEncodingException {
        ImageEntity image = imageService.getImageById(id);
        String filename = image.getName() + "." + image.getExtension();
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString()).replace("+", "%20");

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        switch (image.getExtension().toLowerCase()) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .contentType(mediaType)
                .body(image.getData());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImageById(id);
        return ResponseEntity.ok().build();
    }
}
