package com.backend.BiteBox.service;

import com.backend.BiteBox.Repository.FoodRepository;
import com.backend.BiteBox.entity.FoodEntity;
import com.backend.BiteBox.io.FoodRequest;
import com.backend.BiteBox.io.FoodResponse;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    // Inject the Cloudinary instance here
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private FoodRepository foodRepository;



    // method for uploading image on the cloudinary cloud
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return (String) data.get("secure_url");

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }


    // method is used to add food
    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        FoodEntity newFoodEntity = convertToEntity(request);
        String imageUrl = uploadFile(file);
        newFoodEntity.setImageUrl(imageUrl);
        newFoodEntity = foodRepository.save(newFoodEntity);

        return convertToResponse(newFoodEntity);
    }



    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> databaseEntries = foodRepository.findAll();
        return databaseEntries.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public FoodResponse readFood(String id) {
        FoodEntity exixtingFood = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food Not found: " + id));
        return convertToResponse(exixtingFood);
    }

    // will implement later
    @Override
    public boolean deleteFile(String url) {
        return true;
    }

    // delete by id
    @Override
    public void deleteFood(String id) {
        FoodResponse foodResponse = readFood(id);
        String imageUrl = foodResponse.getImageUrl();
        boolean isDeleted = deleteFile(imageUrl);
        if (isDeleted) {
            foodRepository.deleteById(id);
        } else {
            throw new RuntimeException("Image deletion failed");
        }
    }


    // Converts a FoodRequest object to a FoodEntity object for database storage
    private FoodEntity convertToEntity(FoodRequest request) {
        return FoodEntity.builder()
                .name(request.getName()) // Set food name
                .description(request.getDescription()) // Set food description
                .category(request.getCategory()) // Set food category
                .price(request.getPrice()) // Set food price
                .build(); // Build and return the FoodEntity object
    }

    // Converts a FoodEntity object to a FoodResponse object for API response
    private FoodResponse convertToResponse(FoodEntity entity) {
        return FoodResponse.builder()
                .id(entity.getId()) // Set food ID
                .name(entity.getName()) // Set food name
                .description(entity.getDescription()) // Set food description
                .category(entity.getCategory()) // Set food category
                .price(entity.getPrice()) // Set food price
                .imageUrl(entity.getImageUrl()) // Set food image URL
                .build(); // Build and return the FoodResponse object
    }
}
