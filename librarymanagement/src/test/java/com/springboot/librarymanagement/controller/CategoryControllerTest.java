package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.CategoryRequest;
import com.springboot.librarymanagement.response.CategoryResponse;
import com.springboot.librarymanagement.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    CategoryRequest categoryRequest =new CategoryRequest();
    CategoryResponse categoryResponse = new CategoryResponse();

    List<CategoryResponse> categoryResponseList =new ArrayList<>();

    @BeforeEach
    void setUp() {

        categoryRequest.setName("fantacy");

        categoryResponse.setCategoryid(123L);
        categoryResponse.setCategoryname("fantacy");


    }
    @Test
    public void createTest(){

        when(categoryService.createCategory(categoryRequest)).thenReturn(categoryResponse);
        CategoryResponse categoryResponse = categoryController.create(categoryRequest);
        assertNotNull(categoryResponse);
    }

    @Test
    public void updateTest(){

        when(categoryService.updateCategory(123L, categoryRequest)).thenReturn(categoryResponse);
        CategoryResponse categoryResponse = categoryController.update(123L, categoryRequest);
        assertNotNull(categoryResponse);

    }

    @Test
    public void getTest(){

        when(categoryService.getCategory(123L)).thenReturn(categoryResponse);
        CategoryResponse categoryResponse = categoryController.get(123L);
        assertNotNull(categoryResponse);

    }

    @Test
    public void getAllTest(){

        categoryResponseList.add(categoryResponse);
        when(categoryService.getAllCategories()).thenReturn(categoryResponseList);
        List<CategoryResponse> categoryResponseList = categoryController.getAll();
        assertNotNull(categoryResponseList);

    }

    @Test
    public void deleteTest(){

        categoryService.deleteCategory(123L);
        categoryController.delete(123L);
    }
}
