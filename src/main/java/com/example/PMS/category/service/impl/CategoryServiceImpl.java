
package com.example.PMS.category.service.impl;

import com.example.PMS.category.dto.request.CategoryRequest;
import com.example.PMS.category.dto.response.CategoryResponse;
import com.example.PMS.category.entity.CategoryEntity;
import com.example.PMS.category.mapper.CategoryMapper;
import com.example.PMS.category.repository.CategoryRepository;
import com.example.PMS.category.service.CategoryService;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.config.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public CategoryResponse create(CategoryRequest request) throws IOException {

        // 1. Upload image to Cloudinary first
        if (request.image() == null || request.image().isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }

        Map<String, Object> result =
                cloudinaryService.uploadImage(request.image());

        String imageUrl = result.get("secure_url").toString();
        String publicId = result.get("public_id").toString();

        // 2. Convert request to entity
        CategoryEntity category = categoryMapper.toEntity(request);

        // 3. Set image information before saving
        category.setImageUrl(imageUrl);
        category.setPublicId(publicId);

        // 4. Save category with image information
        CategoryEntity saved = categoryRepository.save(category);

        // 5. Return response
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
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + id
                        )
                );

        return categoryMapper.toResponse(category);
    }


    @Override
    public CategoryResponse update(Integer id, CategoryRequest request)
            throws IOException {

        // 1. Find category
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + id
                        )
                );

        // 2. Store old image public ID
        String oldPublicId = category.getPublicId();

        // 3. Update category fields
        categoryMapper.updateEntity(category, request);

        // 4. Upload new image if provided
        if (request.image() != null && !request.image().isEmpty()) {

            // Upload new image first
            Map<String, Object> result =
                    cloudinaryService.uploadImage(request.image());

            String imageUrl = result.get("secure_url").toString();
            String publicId = result.get("public_id").toString();

            // Update image information
            category.setImageUrl(imageUrl);
            category.setPublicId(publicId);

            // 5. Save updated category
            CategoryEntity updated = categoryRepository.save(category);

            // 6. Delete old image after successful database save
            if (oldPublicId != null && !oldPublicId.isBlank()) {
                try {
                    cloudinaryService.deleteImage(oldPublicId);
                } catch (Exception e) {
                    // Log the error; old image cleanup can be retried later
                    System.err.println(
                            "Failed to delete old category image: "
                                    + oldPublicId + ", " + e.getMessage()
                    );
                }
            }

            return categoryMapper.toResponse(updated);
        }

        // 7. If no new image is provided, keep the existing image
        CategoryEntity updated = categoryRepository.save(category);

        return categoryMapper.toResponse(updated);
    }



    @Override
    public void delete(Integer id) throws IOException {


        // 1. Find category
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + id
                        )
                );

        // 2. Delete image from Cloudinary
        String publicId = category.getPublicId();

        if (publicId != null && !publicId.isBlank()) {
            cloudinaryService.deleteImage(publicId);
        }

        // 3. Delete category from database
        categoryRepository.delete(category);
    }
}

