package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Category;
import com.springboot.librarymanagement.entity.Tag;
import com.springboot.librarymanagement.repository.BookRepository;
import com.springboot.librarymanagement.repository.CategoryRepository;
import com.springboot.librarymanagement.repository.TagRepository;
import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TagRepository tagRepo;

    private BookResponse mapToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setBookid(book.getBookid());
        response.setBooktitle(book.getBooktitle());
        response.setAuthorname(book.getAuthorname());
        response.setIsbn(book.getIsbn());
        return response;
    }

    @Override
    public BookResponse createBook(BookRequest request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Tag> tags = tagRepo.findAllById(request.getTagIds());

        Book book = new Book();
        book.setBooktitle(request.getBooktitle());             // ✅ must set
        book.setAuthorname(request.getAuthorname());           // ✅ must set
        book.setIsbn(request.getIsbn());               // ✅ already working
        book.setCategory(category);                    // ✅ must set
        book.setTags(new HashSet<>(tags));

        bookRepo.save(book);
        return mapToResponse(book);
    }

}
