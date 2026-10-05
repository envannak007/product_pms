package com.example.PMS.carts.controller;

import com.example.PMS.carts.dto.request.AddToCartRequest;
import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.carts.dto.response.CartResponse;
import com.example.PMS.carts.service.AddToCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/add-to-cart")
@RequiredArgsConstructor
public class AddToCartController {
    private final AddToCartService addToCartService;

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<CartResponse>> create(
            @PathVariable Integer userId,
            @Valid @RequestBody AddToCartRequest request
            ){
        CartResponse response = addToCartService.addToCart(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.created("Add to cart create success",response)
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CartResponse>>> getAll(){
        List<CartResponse> response = addToCartService.findAll();
        return  ResponseEntity.ok(
                BaseResponse.ok("Add to cart retrieved success.",response)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<CartResponse>> getByUserId(
            @PathVariable Integer userId
    ){
        CartResponse response = addToCartService.getByUser(userId);
        return  ResponseEntity.ok(
                BaseResponse.ok("Add to cart retrieved success.",response)
        );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<BaseResponse<Void>> deleteCartItem(
            @PathVariable Integer cartItemId
    ) {

        addToCartService.delete(cartItemId);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Cart item deleted successfully.",
                        null
                )
        );
    }
}
