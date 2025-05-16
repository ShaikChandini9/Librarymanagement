package com.springboot.librarymanagement.repository;

import com.springboot.librarymanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
