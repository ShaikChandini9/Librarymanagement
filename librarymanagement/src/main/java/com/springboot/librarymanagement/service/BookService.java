package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest request);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
    BookResponse getBook(Long id);
    List<BookResponse> getAllBooks();
}
