package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.exception.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exception.StationConflictException;
import com.dasvoximon.railwaysystem.exception.StationNotFoundException;
import com.dasvoximon.railwaysystem.exception.TrainNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.repository.RouteRepository;
import com.dasvoximon.railwaysystem.repository.StationRepository;
import com.dasvoximon.railwaysystem.repository.TrainRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    public void addRoute(RouteRequest routeRequest) {
        log.info("Adding new route: {}", routeRequest);

        String trainCode = routeRequest.getTrainCode();
        String originStationCode = routeRequest.getOriginStationCode();
        String destinationStationCode = routeRequest.getDestinationStationCode();

        Train train = trainRepository.findByCode(trainCode)
                .orElseThrow(() -> {
                    log.error("Train not found: {}", trainCode);
                    return new TrainNotFoundException("Train with code: " + trainCode + " doesn't exist");
                });
        Station origin = stationRepository.findByCode(originStationCode)
                .orElseThrow(() -> {
                    log.error("Origin station not found: {}", originStationCode);
                    return new StationNotFoundException("Station with code: " + originStationCode + " doesn't exist");
                });
        Station destination = stationRepository.findByCode(destinationStationCode)
                .orElseThrow(() -> {
                    log.error("Destination station not found: {}", destinationStationCode);
                    return new StationNotFoundException("Station with code: " + destinationStationCode + " doesn't exist");
                });

        if (originStationCode.equals(destinationStationCode)) {
            log.warn("Attempt to add route with same origin and destination: {}", originStationCode);
            throw new StationConflictException("Origin and destination cannot be same.");
        }
        if (routeRepository.existsByOriginStationAndDestinationStation(origin, destination)) {
            log.warn("Duplicate route detected between {} and {}", originStationCode, destinationStationCode);
            throw new StationConflictException("Routes between Stations already exist.");
        }

        Route route = new Route();
        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance(routeRequest.getDistance());

        routeRepository.save(route);
        log.info("Route successfully added between {} and {}", originStationCode, destinationStationCode);
    }

    @Transactional
    public void addRoutes(List<RouteRequest> routeRequests) {
        log.info("Adding multiple routes. Count: {}", routeRequests.size());

        for (RouteRequest routeRequest : routeRequests) {
            addRoute(routeRequest); // reuse single add logic
        }

        log.info("All {} routes added successfully", routeRequests.size());
    }

    public List<Route> getRoutes() {
        log.info("Fetching all routes");
        List<Route> routes = routeRepository.findAll();
        if (routes.isEmpty()) {
            log.error("No routes found in database");
            throw new RouteNotFoundException("No routes found in database");
        }
        log.info("Retrieved {} routes", routes.size());
        return routes;
    }

    public List<RouteDetailsDTO> getRoutesAndStationAndTrain() {
        log.info("Fetching route details with stations and trains");
        List<RouteDetailsDTO> routes = routeRepository.findRoute();
        if (routes.isEmpty()) {
            log.error("No route details found in database");
            throw new RouteNotFoundException("No routes found in database");
        }
        log.info("Retrieved {} route details", routes.size());
        return routes;
    }

    public void updateRoute(Long route_id, RouteRequest routeRequest) {
        log.info("Updating route with ID: {}", route_id);

        Route route = routeRepository.findById(route_id)
                .orElseThrow(() -> {
                    log.error("Route not found with id: {}", route_id);
                    return new RouteNotFoundException("Route with id: " + route_id + " doesn't exist");
                });

        String trainCode = routeRequest.getTrainCode();
        String originStationCode = routeRequest.getOriginStationCode();
        String destinationStationCode = routeRequest.getDestinationStationCode();

        Train train = trainRepository.findByCode(trainCode)
                .orElseThrow(() -> {
                    log.error("Train not found: {}", trainCode);
                    return new TrainNotFoundException("Train with code: " + trainCode + " doesn't exist");
                });
        Station origin = stationRepository.findByCode(originStationCode)
                .orElseThrow(() -> {
                    log.error("Origin station not found: {}", originStationCode);
                    return new StationNotFoundException("Station with code: " + originStationCode + " doesn't exist");
                });
        Station destination = stationRepository.findByCode(destinationStationCode)
                .orElseThrow(() -> {
                    log.error("Destination station not found: {}", destinationStationCode);
                    return new StationNotFoundException("Station with code: " + destinationStationCode + " doesn't exist");
                });

        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistance(routeRequest.getDistance());

        routeRepository.save(route);
        log.info("Route with ID {} successfully updated", route_id);
    }

    public void removeRoute(Long route_id) {
        log.info("Removing route with ID: {}", route_id);

        if (!routeRepository.existsById(route_id)) {
            log.error("Route not found with id: {}", route_id);
            throw new RouteNotFoundException("Route with id: " + route_id + " doesn't exist");
        }

        routeRepository.deleteById(route_id);
        log.info("Route with ID {} successfully removed", route_id);
    }
}
