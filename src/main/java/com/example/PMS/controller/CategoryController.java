package com.example.PMS.controller;

import com.example.PMS.dto.request.CategoryRequest;
import com.example.PMS.dto.response.BaseResponse;
import com.example.PMS.dto.response.CategoryResponse;
import com.example.PMS.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponse>> create(@Valid @RequestBody
                                                                 CategoryRequest request){
        CategoryResponse response = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.created(
                "Category created successfully.",response
        ));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getAll(){
        List<CategoryResponse> response = categoryService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Category retrieved successfully.",response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> getById(
            @PathVariable Integer id
    ){
        CategoryResponse response = categoryService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Category retrieved successfully.",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request
    ){
        CategoryResponse response = categoryService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("Category updated successfully.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id){
        categoryService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Category deleted successfully.",null)
        );
    }
}
