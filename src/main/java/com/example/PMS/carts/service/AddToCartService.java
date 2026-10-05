package com.example.PMS.carts.service;

import com.example.PMS.carts.dto.request.AddToCartRequest;
import com.example.PMS.carts.dto.response.CartResponse;

import java.util.List;

public interface AddToCartService {
    CartResponse addToCart(Integer userId, AddToCartRequest request);
    List<CartResponse> findAll();
    CartResponse getByUser(Integer userId);
    void delete(Integer cartItemId);
}
