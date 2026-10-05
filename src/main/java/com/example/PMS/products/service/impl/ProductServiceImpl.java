package com.example.PMS.products.service.impl;

import com.example.PMS.products.dto.request.ProductRequest;
import com.example.PMS.products.dto.response.ProductResponse;
import com.example.PMS.category.entity.CategoryEntity;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.products.entity.ProductImageEntity;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.products.mapper.ProductMapper;
import com.example.PMS.category.repository.CategoryRepository;
import com.example.PMS.products.repository.ProductImageRepository;
import com.example.PMS.products.repository.ProductRepository;
import com.example.PMS.config.cloudinary.CloudinaryService;
import com.example.PMS.products.service.GTINService;
import com.example.PMS.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final GTINService gtinService;

    @Override
    public ProductResponse create(ProductRequest request) throws IOException {

        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+ request.categoryId()));

        ProductEntity product = productMapper.toEntity(request);
        product.setGtin(gtinService.createGtin13());
        product.setCategory(category);

        // save product
        ProductEntity saved = productRepository.save(product);

        // upload image
        if (request.images() != null && !request.images().isEmpty()) {
            for (MultipartFile image : request.images()){
                if(image.isEmpty()){
                    continue;
                }

                Map<String, Object> result = cloudinaryService.uploadImage(image);
                String imageUrl = (String) result.get("secure_url");
                String publicId = (String) result.get("public_id");

                // create image
                ProductImageEntity imageEntity = new ProductImageEntity();
                imageEntity.setImageUrl(imageUrl);
                imageEntity.setPublicId(publicId);
                imageEntity.setProduct(product);
                // save image
                productImageRepository.save(imageEntity);
                saved.getImage().add(imageEntity);
            }
        }


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
    public ProductResponse update(Integer id, ProductRequest request) throws IOException {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id
                        ));

        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + request.categoryId()
                        ));

        productMapper.updateEntity(product, request);
        product.setCategory(category);

        // save product
        ProductEntity updated = productRepository.save(product);

        // check new images
        if (request.images() != null && !request.images().isEmpty()) {

            // delete old images from Cloudinary
            for (ProductImageEntity oldImage : product.getImage()) {

                if (oldImage != null
                        && oldImage.getPublicId() != null
                        && !oldImage.getPublicId().isBlank()) {

                    cloudinaryService.deleteImage(oldImage.getPublicId());
                }
            }

            // delete old images from database
            product.getImage().clear();

            // upload new images
            for (MultipartFile image : request.images()) {

                if (image == null || image.isEmpty()) {
                    continue;
                }

                Map<String, Object> result =
                        cloudinaryService.uploadImage(image);

                String imageUrl =
                        (String) result.get("secure_url");

                String publicId =
                        (String) result.get("public_id");

                // create new image
                ProductImageEntity imageEntity =
                        new ProductImageEntity();

                imageEntity.setImageUrl(imageUrl);
                imageEntity.setPublicId(publicId);
                imageEntity.setProduct(product);

                // save image
                productImageRepository.save(imageEntity);

                // add to product
                updated.getImage().add(imageEntity);
            }
        }

        return productMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) throws IOException {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : "+id));

        // delete image from cloud
        for (ProductImageEntity oldImage : product.getImage()){
            if (oldImage != null && !oldImage.getPublicId().isBlank()){
                cloudinaryService.deleteImage(oldImage.getPublicId());
            }
        }

        // delete product
        productRepository.delete(product);
    }
}
