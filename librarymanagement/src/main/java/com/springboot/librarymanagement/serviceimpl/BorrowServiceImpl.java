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
import com.springboot.librarymanagement.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BorrowRecordRepository borrowRepo;

    @Value("${borrow.due.days}")
    private int dueDays;

    private static final double FINE_PER_DAY = 2.0;

    @Override
    public BorrowResponse issueBook(IssueBookRequest request) {
        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailabilityCount() <= 0) {
            return new BorrowResponse("Book is not available", 0.0);
        }

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(user);
        record.setIssueDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(dueDays));

        borrowRepo.save(record);

        book.setAvailabilityCount(book.getAvailabilityCount() - 1);
        bookRepo.save(book);

        return new BorrowResponse("Book issued successfully", 0.0);
    }

    @Override
    public BorrowResponse returnBook(ReturnBookRequest request) {
        BorrowRecord record = borrowRepo.findById(request.getBorrowRecordId())
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        record.setReturnDate(LocalDate.now());

        double fine = 0.0;
        if (record.isOverdue()) {
            long overdueDays = record.getOverdueDays();
            fine = overdueDays * FINE_PER_DAY;
            record.setFine(fine);
        }

        Book book = record.getBook();
        book.setAvailabilityCount(book.getAvailabilityCount() + 1); // increment availability

        borrowRepo.save(record);
        bookRepo.save(book);

        return new BorrowResponse("Book returned successfully", fine);
    }

}
