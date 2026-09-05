package com.example.PMS.service;

import com.example.PMS.dto.request.ProductImageRequest;
import com.example.PMS.dto.response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    ProductImageResponse create(ProductImageRequest imageRequest) throws IOException;
    List<ProductImageResponse> findAll();
    ProductImageResponse findById(Integer id);
    List<ProductImageResponse> findByProductId(Integer productId);
    ProductImageResponse update(Integer id, MultipartFile file) throws IOException;
    void delete(Integer id) throws IOException;

}
