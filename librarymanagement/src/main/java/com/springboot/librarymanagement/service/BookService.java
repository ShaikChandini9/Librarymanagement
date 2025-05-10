package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;

public interface BookService {

    BookResponse createBook(BookRequest request);
}
