package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.SearchCriteriaRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.SearchService;
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
public class SearchControllerTest {

    @InjectMocks
    SearchController searchController;

    @Mock
    SearchService searchService;

    List<BookResponse> bookResponseList = new ArrayList<>();
    SearchCriteriaRequest searchCriteriaRequest = new SearchCriteriaRequest();
    BookResponse bookResponse = new BookResponse();

    @BeforeEach
    void setUp() {

        searchCriteriaRequest.setAuthorname("author");
        searchCriteriaRequest.setAvailablecount(true);
        searchCriteriaRequest.setBooktitle("book");
        searchCriteriaRequest.setCategoryName("category");

        bookResponse.setAuthorname("F. Scott Fitzgerald");
        bookResponse.setBooktitle("The Great Gatsby");
        bookResponse.setBookid(123L);
        bookResponse.setIsbn("9780743273565");
    }

    @Test
    public void searchBooksTest(){

        bookResponseList.add(bookResponse);
        when(searchService.searchBooks(searchCriteriaRequest)).thenReturn(bookResponseList);
        List<BookResponse> response=searchController.searchBooks(searchCriteriaRequest);
        assertNotNull(response);
    }
}
