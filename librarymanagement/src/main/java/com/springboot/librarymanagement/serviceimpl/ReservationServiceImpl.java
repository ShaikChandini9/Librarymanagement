package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Reservation;
import com.springboot.librarymanagement.entity.ReservationStatus;
import com.springboot.librarymanagement.exception.ResourceNotFoundException;
import com.springboot.librarymanagement.repository.ReservationRepository;
import com.springboot.librarymanagement.request.ReservationRequest;
import com.springboot.librarymanagement.response.ReservationResponse;
import com.springboot.librarymanagement.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getBookId(),
                reservation.getUserId(),
                reservation.getReservationDate(),
                reservation.getStatus()
        );
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setBookId(request.getBookId());
        reservation.setUserId(request.getUserId());
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return mapToResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    @Override
    public ReservationResponse approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING reservations can be approved.");
        }

        reservation.setStatus(ReservationStatus.APPROVED);
        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    @Override
    public ReservationResponse rejectReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING reservations can be rejected.");
        }

        reservation.setStatus(ReservationStatus.REJECTED);
        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

}
