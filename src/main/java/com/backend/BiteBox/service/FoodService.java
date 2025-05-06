package com.backend.BiteBox.service;

import com.backend.BiteBox.io.FoodRequest;
import com.backend.BiteBox.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    public String uploadFile(MultipartFile file);

    FoodResponse addFood(FoodRequest request, MultipartFile file);

    List<FoodResponse> readFoods();

    FoodResponse readFood(String id);

    boolean deleteFile(String url);

    void deleteFood(String id);
}
