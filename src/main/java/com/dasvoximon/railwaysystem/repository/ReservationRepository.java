package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
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

    @Query("""
      SELECT r FROM Reservation r
      JOIN FETCH r.schedule s
      JOIN FETCH s.route ro
      JOIN FETCH ro.train t
      JOIN FETCH ro.originStation o
      JOIN FETCH ro.destinationStation d
      JOIN FETCH r.passenger p
      WHERE r.pnr = :pnr
    """)
    Optional<Reservation> findFullTicketByPnr(String pnr);
}
