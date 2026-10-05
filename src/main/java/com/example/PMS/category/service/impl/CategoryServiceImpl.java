package com.example.PMS.category.service.impl;
import com.example.PMS.category.dto.request.CategoryRequest;
import com.example.PMS.category.dto.response.CategoryResponse;
import com.example.PMS.category.entity.CategoryEntity;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.category.mapper.CategoryMapper;
import com.example.PMS.category.repository.CategoryRepository;
import com.example.PMS.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        CategoryEntity category = categoryMapper.toEntity(request);
        CategoryEntity saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse findById(Integer id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(Integer id, CategoryRequest request) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+id));
        categoryMapper.updateEntity(category,request);
        CategoryEntity updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+id));
        categoryRepository.delete(category);
    }
}
