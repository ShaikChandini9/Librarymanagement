package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.request.SearchCriteriaRequest;
import com.springboot.librarymanagement.response.BookResponse;

import java.util.List;

public interface SearchService {

    List<BookResponse> searchBooks(SearchCriteriaRequest criteria);
}
