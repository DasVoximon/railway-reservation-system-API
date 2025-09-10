package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/routes")
@Tag(
        name = "Route API",
        description = "Endpoints for managing train routes: create, update, list, and delete routes"
)
public class RouteController {

    private RouteService routeService;

    @PostMapping
    @Operation(
            summary = "Add Route",
            description = "Add a new route to the system by specifying details such as origin, destination, and train."
    )
    public ResponseEntity<String> addRoute(@Valid @RequestBody RouteRequest routeRequest) {
        routeService.addRoute(routeRequest);
        String message = "Route added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(
            summary = "Add Multiple Routes",
            description = "Add multiple routes at once by providing a list of route requests."
    )
    public ResponseEntity<String> addRoutes(@Valid @RequestBody List<RouteRequest> routeRequests) {
        routeService.addRoutes(routeRequests);
        String message = "Routes added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Routes",
            description = "Retrieve all routes currently available in the system."
    )
    public ResponseEntity<List<Route>> getRoutes() {
        return new ResponseEntity<>(routeService.getRoutes(), HttpStatus.FOUND);
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Get Route Summaries",
            description = "Retrieve a detailed list of routes along with their associated stations and trains."
    )
    public ResponseEntity<List<RouteDetailsDTO>> getRoutesAndStationsAndTrains() {
        return new ResponseEntity<>(routeService.getRoutesAndStationAndTrain(), HttpStatus.FOUND);
    }

    @PutMapping("/{route_id}")
    @Operation(
            summary = "Update Route",
            description = "Update the details of an existing route by its ID."
    )
    public ResponseEntity<String> updateRoute(@PathVariable Long route_id,
                                              @Valid @RequestBody RouteRequest routeRequest) {
        routeService.updateRoute(route_id, routeRequest);
        return new ResponseEntity<>("Route updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{route_id}")
    @Operation(
            summary = "Delete Route",
            description = "Delete a route permanently from the system using its ID."
    )
    public ResponseEntity<String> removeRoute(@PathVariable Long route_id) {
        routeService.removeRoute(route_id);
        return new ResponseEntity<>("Route deleted successfully", HttpStatus.OK);
    }
}
