package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("""
        SELECT s FROM Schedule s
        JOIN s.route r
        JOIN r.originStation o
        JOIN r.destinationStation d
        WHERE (:origin IS NULL OR o.state = :origin)
          AND (:destination IS NULL OR d.state = :destination)
          AND (:date IS NULL OR s.travelDate = :date)
        """)
    List<Schedule> search(@Param("origin") String origin,
                          @Param("destination") String destination,
                          @Param("date") LocalDate date);

    @Query("""
        SELECT new com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO (
            s.id,
            s.travelDate,
            s.departureTime,
            s.arrivalTime,
            s.base_fare,
            s.seats,
            t.name,
            t.capacity,
            origin.name,
            origin.city,
            origin.state,
            dest.name,
            dest.city,
            dest.state,
            r.distance
        )
        FROM Schedule s
        JOIN s.route r
        JOIN r.train t
        JOIN r.originStation origin
        JOIN r.destinationStation dest
        ORDER BY s.travelDate
        """)
    List<ScheduleDetailsDTO> findSchedulesSummary();

}
