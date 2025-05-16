package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.ReservationRequest;
import com.springboot.librarymanagement.response.ReservationResponse;
import com.springboot.librarymanagement.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.getReservationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> list = reservationService.getAllReservations();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.cancelReservation(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> approveReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.approveReservation(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> rejectReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.rejectReservation(id);
        return ResponseEntity.ok(response);
    }
}