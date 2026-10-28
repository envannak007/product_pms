package com.example.PMS.category.service;

import com.example.PMS.category.dto.request.CategoryRequest;
import com.example.PMS.category.dto.response.CategoryResponse;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request) throws IOException;
    List<CategoryResponse> findAll();
    CategoryResponse findById(Integer id);
    CategoryResponse update(Integer id,CategoryRequest request) throws IOException;
    void delete(Integer id) throws IOException;
}
