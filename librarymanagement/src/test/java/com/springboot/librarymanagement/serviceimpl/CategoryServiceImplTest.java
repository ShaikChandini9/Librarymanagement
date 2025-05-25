package com.springboot.librarymanagement.serviceimpl;


import com.springboot.librarymanagement.entity.Category;
import com.springboot.librarymanagement.repository.CategoryRepository;
import com.springboot.librarymanagement.request.CategoryRequest;
import com.springboot.librarymanagement.response.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {


    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryRequest categoryRequest;
    private Category savedCategory;

    List<Category> categoryList =new ArrayList<>();

    @BeforeEach
    void setUp() {
        categoryRequest = new CategoryRequest();
        categoryRequest.setName("Science");

        savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Science");
    }

    @Test
    void testCreateCategory() {

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryResponse response = categoryService.createCategory(categoryRequest);
        assertNotNull(response);

    }

    @Test
    public void updateCategoryErrorTest(){

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                categoryService.updateCategory(1L, categoryRequest));

        assertEquals("Category not found", ex.getMessage());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void setCategorySuccessTest() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(savedCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
        CategoryResponse response = categoryService.updateCategory(1L, categoryRequest);
        assertNotNull(response);
    }

    @Test
    public void getCategoryErrorTest(){

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                categoryService.getCategory(1L));

        assertEquals("Category not found", ex.getMessage());
        verify(categoryRepository, never()).save(any());

    }

    @Test
    public void getCategorySuccessTest() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(savedCategory));
        CategoryResponse response = categoryService.getCategory(1L);
        assertNotNull(response);
    }

    @Test
    public void getAllCategoriesTest(){

        categoryList.add(savedCategory);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryResponse> response = categoryService.getAllCategories();
        assertNotNull(response);
    }

    @Test
    public void deleteCategoryTest(){

        categoryRepository.deleteById(1L);
        categoryService.deleteCategory(1L);
    }

}
