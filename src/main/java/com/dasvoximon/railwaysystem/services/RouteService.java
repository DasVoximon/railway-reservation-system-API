/*
 * Manage Routes
    * Add Route
    * Add Multiple Route at once X
    * Get Routes    *  by Train and Stations X
    * Update Stations
    * Delete Stations
 */

package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.dto.RouteRequest;
import com.dasvoximon.railwaysystem.exceptions.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.StationConflictException;
import com.dasvoximon.railwaysystem.exceptions.StationNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.TrainNotFoundException;
import com.dasvoximon.railwaysystem.entities.Route;
import com.dasvoximon.railwaysystem.entities.Station;
import com.dasvoximon.railwaysystem.entities.Train;
import com.dasvoximon.railwaysystem.repositories.RouteRepository;
import com.dasvoximon.railwaysystem.repositories.StationRepository;
import com.dasvoximon.railwaysystem.repositories.TrainRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RouteService {

    private RouteRepository routeRepository;
    private TrainRepository trainRepository;
    private StationRepository stationRepository;

    public void addRoute(RouteRequest routeRequest) {
        Long train_id = routeRequest.getTrain_id();
        Long origin_station_id = routeRequest.getOrigin_station_id();
        Long destination_station_id = routeRequest.getDestination_station_id();

        Train train = trainRepository.findById(train_id)
                        .orElseThrow(() -> new TrainNotFoundException("Train with id: " +  train_id + " doesn't exist"));
        Station origin = stationRepository.findById(origin_station_id)
                        .orElseThrow(() -> new StationNotFoundException("Station with id: " + origin_station_id + " doesn't exist"));
        Station destination = stationRepository.findById(destination_station_id)
                        .orElseThrow(() -> new StationNotFoundException("Station with id: " + destination_station_id + " doesn't exist"));

        if (origin_station_id.equals(destination_station_id)) {
            throw new StationConflictException("Origin and destination cannot be same.");
        }
        if (routeRepository.existsByOriginStationAndDestinationStation(origin, destination)) {
            throw new StationConflictException("Routes between Stations already exist.");
        }

        Route route = new Route();
        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance_km(routeRequest.getDistance_km());
        route.setReservationStatus(routeRequest.getReservationStatus());

        routeRepository.save(route);
    }

    public List<Route> getRoutes() {
        List<Route> routes =  routeRepository.findAll();
        if (routes.isEmpty()) {
            throw new RouteNotFoundException("No routes found in database");
        }

        return routes;
    }

    public void updateRoute(Long route_id, RouteRequest routeRequest) {

        Route route = routeRepository.findById(route_id)
                .orElseThrow(() -> new RouteNotFoundException("Route with id: " + route_id + " doesn't exist"));

        Long train_id = routeRequest.getTrain_id();
        Long origin_station_id = routeRequest.getOrigin_station_id();
        Long destination_station_id = routeRequest.getDestination_station_id();

        Train train = trainRepository.findById(train_id)
                        .orElseThrow(() -> new TrainNotFoundException("Train with id: " + train_id + " doesn't exist"));
        Station origin = stationRepository.findById(origin_station_id)
                        .orElseThrow(() -> new StationNotFoundException("Station with id: " + origin_station_id + " doesn't exist"));
        Station destination = stationRepository.findById(origin_station_id)
                        .orElseThrow(() -> new StationNotFoundException("Station with id: " + destination_station_id + " doesn't exist"));

        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance_km(routeRequest.getDistance_km());
        route.setReservationStatus(routeRequest.getReservationStatus());

        routeRepository.save(route);

    }

    public void removeRoute(Long route_id) {
        if (routeRepository.existsById(route_id)) {
            throw new RouteNotFoundException("Route with id: " + route_id + " doesn't exist");
        }

        routeRepository.deleteById(route_id);
    }
}
