package com.example.PMS.service.ipml;

import com.example.PMS.dto.request.ProductRequest;
import com.example.PMS.dto.response.ProductResponse;
import com.example.PMS.entity.CategoryEntity;
import com.example.PMS.entity.ProductEntity;
import com.example.PMS.exception.ResourceNotFoundException;
import com.example.PMS.mapper.ProductMapper;
import com.example.PMS.repository.CategoryRepository;
import com.example.PMS.repository.ProductRepository;
import com.example.PMS.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {

        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+ request.categoryId()));

        ProductEntity product = productMapper.toEntity(request);

        product.setCategory(category);

        ProductEntity saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    @Override
    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse findById(Integer id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : "+id));

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Integer id, ProductRequest request) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : "+id));

        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+ request.categoryId()));

        productMapper.updateEntity(product,request);

        product.setCategory(category);
        ProductEntity updated = productRepository.save(product);

        return productMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : "+id));
        productRepository.delete(product);
    }
}
