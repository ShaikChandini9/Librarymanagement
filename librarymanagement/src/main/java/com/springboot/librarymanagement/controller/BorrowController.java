package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.IssueBookRequest;
import com.springboot.librarymanagement.request.ReturnBookRequest;
import com.springboot.librarymanagement.response.BorrowResponse;
import com.springboot.librarymanagement.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/issue")
    public ResponseEntity<BorrowResponse> issueBook(@RequestBody IssueBookRequest request) {
        return ResponseEntity.ok(borrowService.issueBook(request));
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowResponse> returnBook(@RequestBody ReturnBookRequest request) {
        return ResponseEntity.ok(borrowService.returnBook(request));
    }
}
