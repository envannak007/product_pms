package com.example.PMS.service;

import com.example.PMS.dto.request.CategoryRequest;
import com.example.PMS.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> findAll();
    CategoryResponse findById(Integer id);
    CategoryResponse update(Integer id,CategoryRequest request);
    void delete(Integer id);
}
