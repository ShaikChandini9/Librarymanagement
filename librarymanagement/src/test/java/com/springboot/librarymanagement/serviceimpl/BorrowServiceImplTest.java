package com.springboot.librarymanagement.serviceimpl;


import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.BorrowRecord;
import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.repository.BookRepository;
import com.springboot.librarymanagement.repository.BorrowRecordRepository;
import com.springboot.librarymanagement.repository.UserRepository;
import com.springboot.librarymanagement.request.IssueBookRequest;
import com.springboot.librarymanagement.request.ReturnBookRequest;
import com.springboot.librarymanagement.response.BorrowResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceImplTest {

    @InjectMocks
    BorrowServiceImpl borrowServiceImpl;
    @Mock
    private BookRepository bookRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private BorrowRecordRepository borrowRepo;

    private Book book;
    private User user;
    private IssueBookRequest request;

    private BorrowRecord borrowRecord;


    @BeforeEach
    public void setUp() {

        book = new Book();
        book.setBookid(1L);
        book.setBooktitle("Test Book");
        book.setAvailabilityCount(3);

        user = new User();
        user.setUserid(100L);
        user.setUsername("john");

        request = new IssueBookRequest();
        request.setBookId(1L);
        request.setUserId(100L);

        borrowRecord = new BorrowRecord();
        borrowRecord.setBorrowid(1L);
        borrowRecord.setBook(book);
        borrowRecord.setIssueDate(LocalDate.now().minusDays(10));
        borrowRecord.setDueDate(LocalDate.now().minusDays(5));

    }

    @Test
    public void issueBookTest(){

        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(userRepo.findById(100L)).thenReturn(Optional.of(user));

        BorrowResponse response = borrowServiceImpl.issueBook(request);
        assertNotNull(response);

    }

    @Test
    public void issueBookCountTest(){

        book = new Book();
        book.setBookid(1L);
        book.setBooktitle("Test Book");
        book.setAvailabilityCount(0);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        Mockito.lenient().when(userRepo.findById(100L)).thenReturn(Optional.of(user));

        BorrowResponse response = borrowServiceImpl.issueBook(request);
        assertNotNull(response);

    }

    @Test
    void testReturnBook_withFine() {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setBorrowRecordId(1L);

        when(borrowRepo.findById(1L)).thenReturn(Optional.of(borrowRecord));

        BorrowResponse response = borrowServiceImpl.returnBook(request);
        assertNotNull(response);

    }

    @Test
    void testReturnBook_noFine() {

        borrowRecord.setIssueDate(LocalDate.now().minusDays(3));
        borrowRecord.setDueDate(LocalDate.now().plusDays(1));

        ReturnBookRequest request = new ReturnBookRequest();
        request.setBorrowRecordId(1L);

        when(borrowRepo.findById(1L)).thenReturn(Optional.of(borrowRecord));

        BorrowResponse response = borrowServiceImpl.returnBook(request);
        assertNotNull(response);

    }

}
