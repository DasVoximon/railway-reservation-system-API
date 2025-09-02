package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
