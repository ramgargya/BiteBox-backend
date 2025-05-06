package com.backend.BiteBox.service;

import com.backend.BiteBox.Repository.CartRepository;
import com.backend.BiteBox.entity.CartEntity;
import com.backend.BiteBox.io.CartRequest;
import com.backend.BiteBox.io.CartResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;


    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findUserId();
        Optional<CartEntity> cartOptional = cartRepository.findByUserId(loggedInUserId);

        CartEntity cart = cartOptional.orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems = cart.getItems();
        cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0) + 1);
        cart.setItems(cartItems);
        cart = cartRepository.save(cart);

        return convertToResponse(cart);

    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null,loggedInUserId, new HashMap<>()));

        return convertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.findUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest cartRequest) {
        String loggedInUserId = userService.findUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Cart is not found"));

        Map<String,Integer> cartItems = entity.getItems();

        if(cartItems.containsKey(cartRequest.getFoodId())) {
            int currentQty = cartItems.get(cartRequest.getFoodId());
            if(currentQty > 0) {
                cartItems.put(cartRequest.getFoodId(), currentQty - 1);
            } else {
                cartItems.remove(cartRequest.getFoodId());
            }
            entity = cartRepository.save(entity);
        }
        return convertToResponse(entity);
    }






    private CartResponse convertToResponse(CartEntity cartEntity) {
        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}

