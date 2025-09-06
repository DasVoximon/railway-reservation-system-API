package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Passenger;
import com.dasvoximon.railwaysystem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPassengerOrderByIdDesc(Passenger passenger);

    @Query("SELECT r.seatNumber FROM Reservation r WHERE r.schedule.id = :scheduleId")
    List<Integer> findReservedSeats(@Param("scheduleId") Long scheduleId);

    List<Reservation> findByPassengerAndPnrOrderByIdDesc(Passenger passenger, String pnr);

    boolean existsByPnr(String pnr);

    void deleteByPnr(String pnr);

    Optional<Reservation> findByPnr(String pnr);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Reservation r " +
            "WHERE r.schedule.id = :scheduleId AND r.seatNumber = :seatNumber")
    boolean existsByScheduleAndSeat(@Param("scheduleId") Long scheduleId,
                                    @Param("seatNumber") Integer seatNumber);

    Optional<Reservation> findByIdAndPassenger_Email(long id, String passengerEmail);
}
