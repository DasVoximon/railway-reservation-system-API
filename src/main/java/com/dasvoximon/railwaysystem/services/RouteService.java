/*
 * Manage Routes
    * Add Route
    * Add Multiple Route at once X
    * Get Routes    *  by Train and Stations X
    * Update Stations
    * Delete Stations
 */

package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.dto.details.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.dto.request.RouteRequest;
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
import jakarta.transaction.Transactional;
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
        String trainCode = routeRequest.getTrainCode();
        String originStationCode = routeRequest.getOriginStationCode();
        String destinationStationCode = routeRequest.getDestinationStationCode();

        Train train = trainRepository.findByCode(trainCode)
                        .orElseThrow(() -> new TrainNotFoundException("Train with code: " +  trainCode + " doesn't exist"));
        Station origin = stationRepository.findByCode(originStationCode)
                        .orElseThrow(() -> new StationNotFoundException("Station with code: " + originStationCode + " doesn't exist"));
        Station destination = stationRepository.findByCode(destinationStationCode)
                        .orElseThrow(() -> new StationNotFoundException("Station with code: " + destinationStationCode + " doesn't exist"));

        if (originStationCode.equals(destinationStationCode)) {
            throw new StationConflictException("Origin and destination cannot be same.");
        }
        if (routeRepository.existsByOriginStationAndDestinationStation(origin, destination)) {
            throw new StationConflictException("Routes between Stations already exist.");
        }

        Route route = new Route();
        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance(routeRequest.getDistance());

        routeRepository.save(route);
    }

    @Transactional
    public void addRoutes(List<RouteRequest> routeRequests) {
        for (RouteRequest routeRequest : routeRequests) {
            String trainCode = routeRequest.getTrainCode();
            String originStationCode = routeRequest.getOriginStationCode();
            String destinationStationCode = routeRequest.getDestinationStationCode();

            Train train = trainRepository.findByCode(trainCode)
                    .orElseThrow(() -> new TrainNotFoundException("Train with code: " + trainCode + " doesn't exist"));
            Station origin = stationRepository.findByCode(originStationCode)
                    .orElseThrow(() -> new StationNotFoundException("Station with code: " + originStationCode + " doesn't exist"));
            Station destination = stationRepository.findByCode(destinationStationCode)
                    .orElseThrow(() -> new StationNotFoundException("Station with code: " + destinationStationCode + " doesn't exist"));

            if (originStationCode.equals(destinationStationCode)) {
                throw new StationConflictException("Origin and destination cannot be the same for station code: " + originStationCode);
            }
            if (routeRepository.existsByOriginStationAndDestinationStation(origin, destination)) {
                throw new StationConflictException("Route between stations " + originStationCode + " and " + destinationStationCode + " already exists.");
            }

            Route route = new Route();
            route.setTrain(train);
            route.setOriginStation(origin);
            route.setDestinationStation(destination);
            route.setDistance(routeRequest.getDistance());

            routeRepository.save(route);
        }
    }


    public List<Route> getRoutes() {
        List<Route> routes =  routeRepository.findAll();
        if (routes.isEmpty()) {
            throw new RouteNotFoundException("No routes found in database");
        }

        return routes;
    }

    public List<RouteDetailsDTO> getRoutesAndStationAndTrain() {
        List<RouteDetailsDTO> routes =  routeRepository.findRoute();
        if (routes.isEmpty()) {
            throw new RouteNotFoundException("No routes found in database");
        }
        return routes;
    }

    public void updateRoute(Long route_id, RouteRequest routeRequest) {

        Route route = routeRepository.findById(route_id)
                .orElseThrow(() -> new RouteNotFoundException("Route with id: " + route_id + " doesn't exist"));

        String trainCode = routeRequest.getTrainCode();
        String originStationCode = routeRequest.getOriginStationCode();
        String destinationStationCode = routeRequest.getDestinationStationCode();

        Train train = trainRepository.findByCode(trainCode)
                        .orElseThrow(() -> new TrainNotFoundException("Train with code: " + trainCode + " doesn't exist"));
        Station origin = stationRepository.findByCode(originStationCode)
                        .orElseThrow(() -> new StationNotFoundException("Station with code: " + originStationCode + " doesn't exist"));
        Station destination = stationRepository.findByCode(originStationCode)
                        .orElseThrow(() -> new StationNotFoundException("Station with code: " + destinationStationCode + " doesn't exist"));

        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance(routeRequest.getDistance());

        routeRepository.save(route);

    }

    public void removeRoute(Long route_id) {
        if (!routeRepository.existsById(route_id)) {
            throw new RouteNotFoundException("Route with id: " + route_id + " doesn't exist");
        }

        routeRepository.deleteById(route_id);
    }
}
