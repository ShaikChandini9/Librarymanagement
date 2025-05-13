package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Category;
import com.springboot.librarymanagement.entity.Tag;
import com.springboot.librarymanagement.repository.BookRepository;
import com.springboot.librarymanagement.repository.CategoryRepository;
import com.springboot.librarymanagement.repository.TagRepository;
import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.response.CategoryResponse;
import com.springboot.librarymanagement.response.TagResponse;
import com.springboot.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        CategoryResponse category = new CategoryResponse();
        category.setCategoryid(book.getCategory().getId());
        category.setCategoryname(book.getCategory().getName());
        response.setCategory(category);

        List<TagResponse> tagResponses = book.getTags().stream().map(tag -> {
            TagResponse tr = new TagResponse();
            tr.setId(tag.getId());
            tr.setName(tag.getName());
            return tr;
        }).collect(Collectors.toList());

        response.setTags(tagResponses);
        return response;
    }

    @Override
    public BookResponse createBook(BookRequest request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Tag> tags = tagRepo.findAllById(request.getTagIds());

        Optional<Book> optionalBook = bookRepo.findByBooktitleAndAuthornameAndIsbn(
                request.getBooktitle(), request.getAuthorname(), request.getIsbn());

        Book book;

        if (optionalBook.isPresent()) {
            // Book exists: increment availability count
            book = optionalBook.get();
            book.setAvailabilityCount(book.getAvailabilityCount() + 1);
        } else {
            // Book doesn't exist: create a new one
            book = new Book();
            book.setBooktitle(request.getBooktitle());
            book.setAuthorname(request.getAuthorname());
            book.setIsbn(request.getIsbn());
            book.setCategory(category);
            //book.setAvailabilityCount(true); // Optional based on availabilityCount
            book.setAvailabilityCount(1); // Initial count
            book.setTags(new HashSet<>(tags));
        }

        bookRepo.save(book);
        return mapToResponse(book);
    }


    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setBooktitle(request.getBooktitle());
        book.setAuthorname(request.getAuthorname());
        book.setIsbn(request.getIsbn());

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);

        List<Tag> tags = tagRepo.findAllById(request.getTagIds());
        book.setTags(new HashSet<>(tags));

        return mapToResponse(bookRepo.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public BookResponse getBook(Long id) {
        return bookRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return  bookRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
