package com.example.PMS.carts.mapper;

import com.example.PMS.carts.dto.response.CartItemResponse;
import com.example.PMS.carts.dto.response.CartResponse;
import com.example.PMS.carts.entitty.CartEntity;
import com.example.PMS.carts.entitty.CartItemEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AddToCartMapper {

    public CartResponse toCartResponse(CartEntity cart) {

        Integer totalItem = cart.getCartItems()
                .stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();

        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(item.getQuantity())
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(this::toCartItemResponse)
                .toList();

        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                items,
                totalItem,
                totalAmount,
                cart.getCreateAt(),
                cart.getUpdateAt()
        );
    }

    public CartItemResponse toCartItemResponse(
            CartItemEntity cartItem
    ) {

        BigDecimal subtotal =
                cartItem.getProduct()
                        .getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        cartItem.getQuantity()
                                )
                        );

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                subtotal
        );
    }
}