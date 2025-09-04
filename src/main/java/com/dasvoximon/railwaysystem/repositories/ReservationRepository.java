package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Passenger;
import com.dasvoximon.railwaysystem.entities.Reservation;
import com.dasvoximon.railwaysystem.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPassengerOrderByIdDesc(Passenger passenger);

    boolean existsByScheduleIdAndSeatNumber(Long scheduleId, Integer seatNumber);

    List<Reservation> findByScheduleId(Long scheduleId);

    @Query("SELECT r.seatNumber FROM Reservation r WHERE r.schedule.id = :scheduleId")
    List<Integer> findReservedSeats(@Param("scheduleId") Long scheduleId);

    List<Reservation> findByPassengerAndPnrOrderByIdDesc(Passenger passenger, String pnr);

    boolean existsByPnr(String pnr);

    void deleteByPnr(String pnr);

}
