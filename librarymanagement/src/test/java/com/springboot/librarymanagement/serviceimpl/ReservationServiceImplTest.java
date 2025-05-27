package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Reservation;
import com.springboot.librarymanagement.entity.ReservationStatus;
import com.springboot.librarymanagement.entity.User;
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

}
