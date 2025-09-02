package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Route;
import com.dasvoximon.railwaysystem.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Boolean existsByOriginStationAndDestinationStation(
            Station origin_station_id, Station destination_station_id);
}
