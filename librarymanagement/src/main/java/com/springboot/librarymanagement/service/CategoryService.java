package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.request.CategoryRequest;
import com.springboot.librarymanagement.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    CategoryResponse getCategory(Long id);
    List<CategoryResponse> getAllCategories();
    void deleteCategory(Long id);
}
