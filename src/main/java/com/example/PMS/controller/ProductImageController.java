package com.example.PMS.controller;

import com.example.PMS.dto.request.ProductImageRequest;
import com.example.PMS.dto.response.BaseResponse;
import com.example.PMS.dto.response.ProductImageResponse;
import com.example.PMS.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-image")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductImageResponse>> create(
            @Valid @ModelAttribute ProductImageRequest imageRequest
            ) throws IOException {
        ProductImageResponse response = productImageService.create(imageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.created("Product image created successfully.",response)
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductImageResponse>>> getAll(){
        List<ProductImageResponse> responses = productImageService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Products image retrieved successfully",responses)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductImageResponse>> getById(
            @PathVariable Integer id
    ){
        ProductImageResponse response = productImageService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Products retrieved successfully",response)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<BaseResponse<List<ProductImageResponse>>> getByProductId(
            @PathVariable Integer productId
    ){
        List<ProductImageResponse> responses = productImageService.findByProductId(productId);
        return ResponseEntity.ok(
                BaseResponse.ok("Products image retrieved successfully",responses)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductImageResponse>> update(
            @PathVariable Integer id,
            @RequestParam("file")MultipartFile file
            ) throws IOException{
        ProductImageResponse response = productImageService.update(id,file);
        return ResponseEntity.ok(
                BaseResponse.ok("Product image updated successfully.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id) throws IOException{
        productImageService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Product image deleted successfully.",null)
        );
    }
}
