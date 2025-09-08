package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.service.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private RouteService routeService;

    @PostMapping
    public ResponseEntity<String> addRoute(@Valid @RequestBody RouteRequest routeRequest) {
        routeService.addRoute(routeRequest);
        String message = "Route added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addRoutes(@Valid @RequestBody List<RouteRequest> routeRequests) {
        routeService.addRoutes(routeRequests);
        String message = "Routes added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes() {
        return new ResponseEntity<>(routeService.getRoutes(), HttpStatus.FOUND);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<RouteDetailsDTO>> getRoutesAndStationsAndTrains() {
        return new ResponseEntity<>(routeService.getRoutesAndStationAndTrain(), HttpStatus.FOUND);
    }

    @PutMapping("/{route_id}")
    public ResponseEntity<String> updateRoute(@PathVariable Long route_id,
                                              @Valid @RequestBody RouteRequest routeRequest) {
        routeService.updateRoute(route_id, routeRequest);
        return new ResponseEntity<>("Route updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{route_id}")
    public ResponseEntity<String> removeRoute(@PathVariable Long route_id) {
        routeService.removeRoute(route_id);
        return new ResponseEntity<>("Route deleted successfully", HttpStatus.OK);
    }
}
