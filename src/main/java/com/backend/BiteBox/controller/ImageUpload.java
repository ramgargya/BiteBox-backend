package com.backend.BiteBox.controller;

import com.backend.BiteBox.service.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageUpload {

    @Autowired
    private FoodServiceImpl foodServiceImpl;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        String imageUrl = foodServiceImpl.uploadFile(file);
        Map<String, String> secureUrl = Map.of("secure_url", imageUrl);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }
}
