package com.example.PMS.products.service;

import com.example.PMS.products.dto.request.ProductRequest;
import com.example.PMS.products.dto.response.ProductResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request) throws IOException;
    List<ProductResponse> findAll();
    ProductResponse findById(Integer id);
    ProductResponse update(Integer id,ProductRequest request) throws IOException;
    void delete(Integer id) throws IOException;

}
