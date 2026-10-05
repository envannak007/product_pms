package com.example.PMS.products.controller;

import com.example.PMS.products.dto.request.ProductRequest;
import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.products.dto.response.ProductResponse;
import com.example.PMS.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ProductResponse>> create(
            @Valid @ModelAttribute ProductRequest request) throws IOException {
        ProductResponse response = productService.create(request) ;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(
            value = {"/{id}"},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse<ProductResponse>> update(
            @PathVariable Integer id,
            @Valid @ModelAttribute ProductRequest request) throws  IOException{
        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Product updated successfully",
                        response)
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id) throws IOException{
        productService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Product deleted successfully",
                        null
                )
        );

    }
}
