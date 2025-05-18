package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.IssueBookRequest;
import com.springboot.librarymanagement.request.ReturnBookRequest;
import com.springboot.librarymanagement.response.BorrowResponse;
import com.springboot.librarymanagement.service.BorrowService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowControllerTest {

    @InjectMocks
    private BorrowController borrowController;

    @Mock
    private BorrowService borrowService;

    IssueBookRequest issueBookRequest = new IssueBookRequest();
    BorrowResponse borrowResponse = new BorrowResponse();
    ReturnBookRequest returnBookRequest = new ReturnBookRequest();

    @BeforeEach
    public void setUp() {

        issueBookRequest.setBookId(123L);
        issueBookRequest.setUserId(456L);

        borrowResponse.setFine(1.2);
        borrowResponse.setMessage("fine has been created");

        returnBookRequest.setBorrowRecordId(456L);
    }

    @Test
    public void issueBookTest(){

        when(borrowService.issueBook(issueBookRequest)).thenReturn(borrowResponse);
        ResponseEntity<BorrowResponse> borrow= borrowController.issueBook(issueBookRequest);
        assertEquals(HttpStatus.OK,borrow.getStatusCode());

    }

    @Test
    public void returnBookTest(){

        when(borrowService.returnBook(returnBookRequest)).thenReturn(borrowResponse);
        ResponseEntity<BorrowResponse> returnBook= borrowController.returnBook(returnBookRequest);
        assertEquals(HttpStatus.OK,returnBook.getStatusCode());

    }
}
