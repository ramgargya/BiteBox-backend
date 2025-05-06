package com.backend.BiteBox.service;

import com.backend.BiteBox.io.CartRequest;
import com.backend.BiteBox.io.CartResponse;
import org.springframework.stereotype.Service;

public interface CartService {
    CartResponse addToCart(CartRequest Request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}
