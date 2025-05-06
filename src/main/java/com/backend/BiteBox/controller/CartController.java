package com.backend.BiteBox.controller;

import com.backend.BiteBox.io.CartRequest;
import com.backend.BiteBox.io.CartResponse;
import com.backend.BiteBox.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addToCart(@RequestBody CartRequest request) {
        String foodId = request.getFoodId();
        if(foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FoodId not found");
        }

        return cartService.addToCart(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart();
    }

    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse removeFromCart(@RequestBody CartRequest request) {
        String foodId = request.getFoodId();
        if(foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FoodId not found");
        }

        return cartService.removeFromCart(request);
    }

}
