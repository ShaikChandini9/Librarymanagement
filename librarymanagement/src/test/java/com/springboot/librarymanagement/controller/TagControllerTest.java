package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.TagRequest;
import com.springboot.librarymanagement.response.TagResponse;
import com.springboot.librarymanagement.service.TagService;
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
public class TagControllerTest {

    @InjectMocks
    TagController tagController;

    @Mock
    TagService tagService;

    TagRequest tagRequest = new TagRequest();
    TagResponse tagResponse = new TagResponse();

    List<TagResponse> tagResponseList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        tagRequest.setName("test");

        tagResponse.setName("test");
        tagResponse.setId(123L);

        tagResponseList.add(tagResponse);
    }

    @Test
    public void createTest(){

        when(tagService.createTag(tagRequest)).thenReturn(tagResponse);
        TagResponse tagResponse=tagController.create(tagRequest);
        assertNotNull(tagResponse);
    }

    @Test
    public void updateTest(){

        when(tagService.updateTag(123L, tagRequest)).thenReturn(tagResponse);
        TagResponse tagResponse=tagController.update(123L, tagRequest);
        assertNotNull(tagResponse);
    }

    @Test
    public void getTest(){

        when(tagService.getTag(123L)).thenReturn(tagResponse);
        TagResponse tagResponse=tagController.get(123L);
        assertNotNull(tagResponse);
    }

    @Test
    public void getAllTest(){

        when(tagService.getAllTags()).thenReturn(tagResponseList);
        List<TagResponse> list=tagController.getAll();
        assertNotNull(list);
    }

    @Test
    public void deleteTest(){

        tagService.deleteTag(123L);
        tagController.delete(123L);
    }
}
