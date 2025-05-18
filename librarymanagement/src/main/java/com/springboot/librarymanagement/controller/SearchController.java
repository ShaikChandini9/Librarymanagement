package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.SearchCriteriaRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public List<BookResponse> searchBooks(@RequestBody SearchCriteriaRequest criteria) {
        return searchService.searchBooks(criteria);
    }
}
