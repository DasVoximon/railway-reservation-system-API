package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.dto.details.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.entities.Route;
import com.dasvoximon.railwaysystem.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Boolean existsByOriginStationAndDestinationStation(
            Station origin_station_id, Station destination_station_id);

    @Query("""
        SELECT new com.dasvoximon.railwaysystem.dto.details.RouteDetailsDTO (
            r.id,
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
        FROM Route r
        JOIN r.train t
        JOIN r.originStation origin
        JOIN r.destinationStation dest
        ORDER BY t.name ASC
        """)
    List<RouteDetailsDTO> findRoute();
}
