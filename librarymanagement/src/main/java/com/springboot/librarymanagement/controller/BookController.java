package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIBRARIAN')")
    @PostMapping("/add")
    public BookResponse create(@RequestBody BookRequest request) {
        return bookService.createBook(request);
    }

    @PutMapping("/update/{id}")
    public BookResponse update(@PathVariable Long id, @RequestBody BookRequest request) {
        return bookService.updateBook(id, request);
    }

    @GetMapping("/get-by/{id}")
    public BookResponse get(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/get-all-details")
    public List<BookResponse> getAll() {
        return bookService.getAllBooks();
    }

    @DeleteMapping("/delete-by/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
