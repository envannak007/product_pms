package com.example.PMS.carts.service.impl;

import com.example.PMS.carts.dto.request.AddToCartRequest;
import com.example.PMS.carts.dto.response.CartResponse;
import com.example.PMS.carts.entitty.CartEntity;
import com.example.PMS.carts.entitty.CartItemEntity;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.common.exception.BadRequestException;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.carts.mapper.AddToCartMapper;
import com.example.PMS.carts.repository.CartItemRepository;
import com.example.PMS.carts.repository.CartRepository;
import com.example.PMS.products.repository.ProductRepository;
import com.example.PMS.users.repository.UserRepository;
import com.example.PMS.carts.service.AddToCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddToCartServiceImpl implements AddToCartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AddToCartMapper addToCartMapper;
    @Override
    @Transactional
    public CartResponse addToCart(Integer userId,AddToCartRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " +userId));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(()->{
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : " + request.productId()));

        CartItemEntity cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (cartItem != null){
            int newQuantity = cartItem.getQuantity() + request.quantity();

            if (newQuantity > product.getStock()) {
                throw new BadRequestException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            cartItem.setQuantity(newQuantity);
        }else {

            if (product.getStock() < request.quantity()) {
                throw new BadRequestException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            cartItem = new CartItemEntity();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.quantity());

            cart.getCartItems().add(cartItem);

        }
        cartItemRepository.save(cartItem);

        return addToCartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartResponse> findAll() {
        return cartRepository.findAll()
                .stream()
                .map(addToCartMapper::toCartResponse)
                .toList();
    }

    @Override
    @Transactional
    public CartResponse getByUser(Integer userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found with user id : "+ userId));
        return addToCartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public void delete(Integer cartItemId) {

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new ResourceNotFoundException("Cart item not found with id : " + cartItemId));
        cartItemRepository.delete(cartItem);
    }
}
