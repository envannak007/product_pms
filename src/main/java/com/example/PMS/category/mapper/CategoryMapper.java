package com.example.PMS.category.mapper;

import com.example.PMS.category.dto.request.CategoryRequest;
import com.example.PMS.category.dto.response.CategoryResponse;
import com.example.PMS.category.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryRequest request){
        CategoryEntity category = new CategoryEntity();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }

    public CategoryResponse toResponse(CategoryEntity category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreateAt(),
                category.getUpdateAt()
        );
    }

    public void updateEntity(
            CategoryEntity category,
            CategoryRequest request
    ){
        category.setName(request.name());
        category.setDescription(request.description());
    }
}
