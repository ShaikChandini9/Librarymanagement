package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.entity.ReservationStatus;
import com.springboot.librarymanagement.request.ReservationRequest;
import com.springboot.librarymanagement.response.ReservationResponse;
import com.springboot.librarymanagement.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @InjectMocks
    ReservationController reservationController;

    @Mock
    ReservationService reservationService;

    LocalDateTime dateTime = LocalDateTime.now();

    ReservationStatus reservationStatus;

    List<ReservationResponse> reservations =new ArrayList<>();

    ReservationRequest reservationRequest = new ReservationRequest(123L,456L);
    ReservationResponse response=new ReservationResponse(123L, 456L,789L, dateTime, reservationStatus );

    @BeforeEach
    void setUp() {

        reservations.add(response);
    }

    @Test
    public void createTest() {

        when(reservationService.createReservation(reservationRequest)).thenReturn(response);
        ResponseEntity<ReservationResponse> responseEntity = reservationController.createReservation(reservationRequest);
        assertNotNull(responseEntity);
    }
    @Test
    public void getReservationTest(){

        when(reservationService.getReservationById(123L)).thenReturn(response);
        ResponseEntity<ReservationResponse> responseEntity = reservationController.getReservation(123L);
        assertNotNull(responseEntity);
    }

    @Test
    public void getAllReservationsTest(){


        when(reservationService.getAllReservations()).thenReturn(reservations);
        ResponseEntity<List<ReservationResponse>> responseEntity = reservationController.getAllReservations();
        assertNotNull(responseEntity);
    }

    @Test
    public void cancelReservationTest(){

        when(reservationService.cancelReservation(123L)).thenReturn(response);
        ResponseEntity<ReservationResponse> responseEntity = reservationController.cancelReservation(123L);
        assertNotNull(responseEntity);
    }

    @Test
    public void approveReservationTest(){

        when(reservationService.approveReservation(123L)).thenReturn(response);
        ResponseEntity<ReservationResponse> responseEntity = reservationController.approveReservation(123L);
        assertNotNull(responseEntity);
    }

    @Test
    public void rejectReservationTest(){

        when(reservationService.rejectReservation(123L)).thenReturn(response);
        ResponseEntity<ReservationResponse> responseEntity = reservationController.rejectReservation(123L);
        assertNotNull(responseEntity);
    }

}
