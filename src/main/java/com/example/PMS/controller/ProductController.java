package com.example.PMS.controller;

import com.example.PMS.dto.request.ProductRequest;
import com.example.PMS.dto.response.BaseResponse;
import com.example.PMS.dto.response.ProductResponse;
import com.example.PMS.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request){
        ProductResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body( BaseResponse.created(
                "Product created successfully",response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getAll(){
        List<ProductResponse> response = productService.findAll();
        return  ResponseEntity.ok(
                BaseResponse.ok("Products retrieved successfully", response)

        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> getById(@PathVariable Integer id){
        ProductResponse response = productService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Products retrieved successfully",
                        response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> update(@PathVariable Integer id,
                                                                @Valid @RequestBody ProductRequest request){
        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Product updated successfully",
                        response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> delete(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Product deleted successfully",
                        null
                )
        );

    }
}
