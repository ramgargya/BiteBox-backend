package com.backend.BiteBox.controller;

import com.backend.BiteBox.io.FoodRequest;
import com.backend.BiteBox.io.FoodResponse;
import com.backend.BiteBox.service.FoodService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@AllArgsConstructor
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString,
                                @RequestPart("file") MultipartFile file) {
        // Create an instance of ObjectMapper to parse the JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest foodRequest = null;

        try {
            // Convert the JSON string into a FoodRequest object
            foodRequest = objectMapper.readValue(foodString, FoodRequest.class);
        } catch (JsonProcessingException exception) {
            // Throw an exception with a 400 BAD REQUEST status if JSON parsing fails
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        }

        // Call the service layer to add the food and upload the file
        FoodResponse foodResponse = foodService.addFood(foodRequest, file);
        // Return the response containing the added food details
        return foodResponse;
    }


    // returning all the foods
    @GetMapping
    public List<FoodResponse> readFoods() {
        return foodService.readFoods();
    }

    // returning food by id
    @GetMapping("/{id}")
    public FoodResponse readFood(@PathVariable String id) {
        return foodService.readFood(id);
    }

    // deleting food by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id) {
        foodService.deleteFood(id);
    }
}
