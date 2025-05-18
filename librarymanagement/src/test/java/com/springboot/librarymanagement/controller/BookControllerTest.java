package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.BookRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    BookRequest bookRequest = new BookRequest();
    BookResponse bookResponse = new BookResponse();

    List<BookResponse> bookResponseList = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        bookRequest.setAuthorname("F. Scott Fitzgerald");
        bookRequest.setBooktitle("The Great Gatsby");
        bookRequest.setIsbn("9780743273565");
        bookRequest.setCategoryId(123L);

        bookResponse.setAuthorname("F. Scott Fitzgerald");
        bookResponse.setBooktitle("The Great Gatsby");
        bookResponse.setBookid(123L);
        bookResponse.setIsbn("9780743273565");
    }

    @Test
    public void createTest(){
        when(bookService.createBook(bookRequest)).thenReturn(bookResponse);
        BookResponse bookResponse = bookController.create(bookRequest);
        assertNotNull(bookResponse);
    }

    @Test
    public void updateTest(){

        when(bookService.updateBook(123L, bookRequest)).thenReturn(bookResponse);
        BookResponse bookresponse=bookController.update(123L, bookRequest);
        assertNotNull(bookresponse);

    }

    @Test
    public void getTest(){

        when(bookService.getBook(123L)).thenReturn(bookResponse);
        BookResponse bookResponse=bookController.get(123L);
        assertNotNull(bookResponse);

    }

    @Test
    public void getAllTest(){

        bookResponseList.add(bookResponse);
        when(bookService.getAllBooks()).thenReturn(bookResponseList);
        List<BookResponse> bookResponse=bookController.getAll();
        assertNotNull(bookResponse);
    }

    @Test
    public void deleteTest(){

        bookService.deleteBook(123L);
        bookController.delete(123L);
    }
}
