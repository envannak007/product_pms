package com.example.PMS.service;

import com.example.PMS.dto.request.ProductRequest;
import com.example.PMS.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    List<ProductResponse> findAll();
    ProductResponse findById(Integer id);
    ProductResponse update(Integer id,ProductRequest request);
    void delete(Integer id);

}
