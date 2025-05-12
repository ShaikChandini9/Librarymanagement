package com.springboot.librarymanagement.response;

public class BorrowResponse {

    private String message;
    private Double fine;

    public BorrowResponse() {}

    public BorrowResponse(String message, Double fine) {
        this.message = message;
        this.fine = fine;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }
}
