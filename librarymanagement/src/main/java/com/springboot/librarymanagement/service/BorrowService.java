package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.request.IssueBookRequest;
import com.springboot.librarymanagement.request.ReturnBookRequest;
import com.springboot.librarymanagement.response.BorrowResponse;

public interface BorrowService {

    BorrowResponse issueBook(IssueBookRequest request);
    BorrowResponse returnBook(ReturnBookRequest request);
}
