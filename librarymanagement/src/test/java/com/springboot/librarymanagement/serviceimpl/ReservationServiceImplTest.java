package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Reservation;
import com.springboot.librarymanagement.entity.ReservationStatus;
import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.exception.ResourceNotFoundException;
import com.springboot.librarymanagement.repository.ReservationRepository;
import com.springboot.librarymanagement.request.ReservationRequest;
import com.springboot.librarymanagement.response.ReservationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationServiceImpl reservationServiceImpl;

    ReservationRequest reservationRequest = new ReservationRequest(1L,1L);
    Reservation reservation;

    List<Reservation> reservations =new ArrayList<>();

    @BeforeEach
    public void setUp() {

        Book book = new Book();
        book.setBookid(1L);

        User user = new User();
        user.setUserid(2L);

        reservation = new Reservation();
        reservation.setId(100L);
        reservation.setBookId(1L);
        reservation.setUserId(1L);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);
    }

    @Test
    public void createReservationTest(){

        when(reservationRepository.save(any())).thenReturn(reservation);
        ReservationResponse reservationResponse=reservationServiceImpl.createReservation(reservationRequest);
        assertNotNull(reservationResponse);
    }

    @Test
    public void getReservationErrorTest(){

        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationServiceImpl.getReservationById(999L);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationRepository, times(1)).findById(999L);
    }

    @Test
    public void getReservationSuccessTest(){

        when(reservationRepository.findById(999L)).thenReturn(Optional.of(reservation));
        ReservationResponse reservationResponse = reservationServiceImpl.getReservationById(999L);
        assertEquals(reservationResponse.getId(), reservation.getId());
    }

    @Test
    public void getAllReservationsTest(){

        reservations.add(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<ReservationResponse> reservationResponses = reservationServiceImpl.getAllReservations();
        assertEquals(reservationResponses.size(), reservations.size());

    }

    @Test
    public void cancelReservationErrorTest(){

        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationServiceImpl.cancelReservation(999L);
        });

        assertEquals("Reservation not found", exception.getMessage());
        verify(reservationRepository, times(1)).findById(999L);
    }

    @Test
    public void cancelReservationSuccessTest(){

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationResponse reservationResponse = reservationServiceImpl.cancelReservation(1L);
        assertEquals(reservationResponse.getId(), reservation.getId());
    }

    @Test
    public void approveReservationErrorTest(){

        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                reservationServiceImpl.approveReservation(999L));

        assertEquals("Reservation not found with id : '999'", ex.getMessage());
        verify(reservationRepository, times(1)).findById(999L);

    }

    @Test
    public void approveReservationSuccessTest(){

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationResponse reservationResponse = reservationServiceImpl.approveReservation(1L);
        assertEquals(reservationResponse.getId(), reservation.getId());
    }

    @Test
    void approveReservation_ShouldThrowIllegalStateException_WhenStatusIsNotPending() {

        reservation.setStatus(ReservationStatus.APPROVED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                reservationServiceImpl.approveReservation(1L));

        assertEquals("Only PENDING reservations can be approved.", ex.getMessage());
        verify(reservationRepository).findById(1L);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void rejectReservation_ShouldReject_WhenStatusIsPending() {

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservationResponse response = reservationServiceImpl.rejectReservation(1L);

        assertNotNull(response);
    }

    @Test
    void rejectReservation_ShouldThrowResourceNotFoundException_WhenReservationNotFound() {

        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                reservationServiceImpl.rejectReservation(999L));

        assertEquals("Reservation not found with id : '999'", exception.getMessage());
        verify(reservationRepository).findById(999L);
    }

    @Test
    void rejectReservation_ShouldThrowIllegalStateException_WhenStatusIsNotPending() {
        reservation.setStatus(ReservationStatus.APPROVED); // Already approved
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                reservationServiceImpl.rejectReservation(1L));

        assertEquals("Only PENDING reservations can be rejected.", exception.getMessage());
        verify(reservationRepository).findById(1L);
        verify(reservationRepository, never()).save(any());
    }
}
