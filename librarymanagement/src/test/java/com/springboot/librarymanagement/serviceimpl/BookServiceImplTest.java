package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Category;
import com.springboot.librarymanagement.entity.Tag;
import com.springboot.librarymanagement.repository.BookRepository;
import com.springboot.librarymanagement.repository.CategoryRepository;
import com.springboot.librarymanagement.repository.TagRepository;
import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookServiceImpl;

    @Mock
    BookRepository bookRepo;

    @Mock
    CategoryRepository categoryRepo;

    @Mock
    TagRepository tagRepo;

    private BookRequest request;
    private Category mockCategory;
    private Tag tag1 =new Tag();
    private Tag tag2=new Tag();

    Book existingBook = new Book();

    List<Book> books = new ArrayList<>();


    @BeforeEach
    void setUp() {
        request = new BookRequest();
        request.setBooktitle("Test Book");
        request.setAuthorname("Author");
        request.setIsbn("123456");
        request.setCategoryId(1L);
        request.setTagIds(List.of(10L, 20L));

        mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Fiction");

        tag1.setId(10L);
        tag1.setName("Adventure");

        tag2.setId(20L);
        tag2.setName("Science");
    }

    @Test
    void testCreateBook_NewBook_Success() {

        when(categoryRepo.findById(1L)).thenReturn(Optional.of(mockCategory));
        when(tagRepo.findAllById(List.of(10L, 20L))).thenReturn(List.of(tag1, tag2));
        when(bookRepo.findByBooktitleAndAuthornameAndIsbn("Test Book", "Author", "123456"))
                .thenReturn(Optional.empty());

        BookResponse response = bookServiceImpl.createBook(request);
        assertNotNull(response);

    }

    @Test
    void testCreateBook_ExistingBook_IncrementsAvailability() {

        existingBook.setBooktitle("Test Book");
        existingBook.setAuthorname("Author");
        existingBook.setIsbn("123456");
        existingBook.setCategory(mockCategory);
        existingBook.setAvailabilityCount(3);
        existingBook.setTags(new HashSet<>(List.of(tag1)));

        when(categoryRepo.findById(1L)).thenReturn(Optional.of(mockCategory));
        when(tagRepo.findAllById(List.of(10L, 20L))).thenReturn(List.of(tag1, tag2));
        when(bookRepo.findByBooktitleAndAuthornameAndIsbn("Test Book", "Author", "123456"))
                .thenReturn(Optional.of(existingBook));

        BookResponse response = bookServiceImpl.createBook(request);
        assertNotNull(response);
    }

    @Test
    public void updateBookTest(){

        when(bookRepo.findById(1L)).thenReturn(Optional.of(existingBook));
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookServiceImpl.updateBook(1L, request));

        assertEquals("Category not found", ex.getMessage());
        verify(bookRepo, never()).save(any());

    }

    @Test
    void testUpdateBook_Success() {

        when(bookRepo.findById(1L)).thenReturn(Optional.of(existingBook));
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(mockCategory));
        when(tagRepo.findAllById(List.of(10L, 20L))).thenReturn(List.of(tag1, tag2));
        when(bookRepo.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookResponse response = bookServiceImpl.updateBook(1L, request);
        assertNotNull(response);

    }

    @Test
    public void deleteBookTest(){
        bookRepo.deleteById(1L);
        bookServiceImpl.deleteBook(1L);
    }

    @Test
    void testGetBook_NotFound() {
        when(bookRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookServiceImpl.getBook(99L);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void getAllBooksTest(){


        mockCategory.setId(1L);
        mockCategory.setName("Fiction");
        existingBook.setCategory(mockCategory);

        books.add(existingBook);
        when(bookRepo.findAll()).thenReturn(books);
        List<BookResponse> bookResponseList = bookServiceImpl.getAllBooks();
        assertNotNull(bookResponseList);
    }

}
