package com.springboot.librarymanagement.response;

import com.springboot.librarymanagement.entity.ReservationStatus;

import java.time.LocalDateTime;

public class ReservationResponse {

    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDateTime reservationDate;
    private ReservationStatus status;

    public ReservationResponse(Long id, Long bookId, Long userId, LocalDateTime reservationDate, ReservationStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}