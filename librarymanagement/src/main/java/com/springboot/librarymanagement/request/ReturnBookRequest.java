package com.springboot.librarymanagement.request;

public class ReturnBookRequest {

    private Long borrowRecordId;

    public Long getBorrowRecordId() {
        return borrowRecordId;
    }

    public void setBorrowRecordId(Long borrowRecordId) {
        this.borrowRecordId = borrowRecordId;
    }
}
