package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.request.ReservationRequest;
import com.springboot.librarymanagement.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getAllReservations();
    ReservationResponse cancelReservation(Long id);
    ReservationResponse approveReservation(Long reservationId);
    ReservationResponse rejectReservation(Long reservationId);

}
