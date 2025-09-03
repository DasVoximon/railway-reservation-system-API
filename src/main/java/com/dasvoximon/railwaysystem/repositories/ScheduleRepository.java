package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.route.originStation.code = :origin " +
            "AND s.route.destinationStation.code = :destination " +
            "AND s.travelDate = :date")
    List<Schedule> search(@Param("origin") String origin,
                          @Param("destination") String destination,
                          @Param("date") LocalDate date);
}
