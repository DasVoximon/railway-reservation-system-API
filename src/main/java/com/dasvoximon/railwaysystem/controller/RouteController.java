package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/routes")
@Tag(
        name = "Route API",
        description = "Endpoints for managing train routes: create, update, list, and delete routes"
)
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @Operation(
            summary = "Add Route",
            description = "Add a new route to the system by specifying details such as origin, destination, and train."
    )
    public ResponseEntity<String> addRoute(@Valid @RequestBody RouteRequest routeRequest) {
        log.info("[RouteController] Adding new route: origin={}, destination={}, trainCode={}",
                routeRequest.getOriginStationCode(),
                routeRequest.getDestinationStationCode(),
                routeRequest.getTrainCode());

        routeService.addRoute(routeRequest);
        log.debug("[RouteController] Route added successfully: {}", routeRequest);

        return new ResponseEntity<>("Route added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(
            summary = "Add Multiple Routes",
            description = "Add multiple routes at once by providing a list of route requests."
    )
    public ResponseEntity<String> addRoutes(@Valid @RequestBody List<RouteRequest> routeRequests) {
        log.info("[RouteController] Adding {} routes", routeRequests.size());
        routeService.addRoutes(routeRequests);
        log.debug("[RouteController] {} routes added successfully", routeRequests.size());
        return new ResponseEntity<>("Routes added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Routes",
            description = "Retrieve all routes currently available in the system."
    )
    public ResponseEntity<List<Route>> getRoutes() {
        log.info("[RouteController] Fetching all routes");
        List<Route> routes = routeService.getRoutes();
        log.debug("[RouteController] Retrieved {} routes", routes.size());
        return new ResponseEntity<>(routes, HttpStatus.FOUND);
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Get Route Summaries",
            description = "Retrieve a detailed list of routes along with their associated stations and trains."
    )
    public ResponseEntity<List<RouteDetailsDTO>> getRoutesAndStationsAndTrains() {
        log.info("[RouteController] Fetching route summaries");
        List<RouteDetailsDTO> summaries = routeService.getRoutesAndStationAndTrain();
        log.debug("[RouteController] Retrieved {} route summaries", summaries.size());
        return new ResponseEntity<>(summaries, HttpStatus.FOUND);
    }

    @PutMapping("/{route_id}")
    @Operation(
            summary = "Update Route",
            description = "Update the details of an existing route by its ID."
    )
    public ResponseEntity<String> updateRoute(@PathVariable Long route_id,
                                              @Valid @RequestBody RouteRequest routeRequest) {
        log.info("[RouteController] Updating route with ID={}", route_id);
        routeService.updateRoute(route_id, routeRequest);
        log.debug("[RouteController] Route {} updated successfully", route_id);
        return new ResponseEntity<>("Route updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{route_id}")
    @Operation(
            summary = "Delete Route",
            description = "Delete a route permanently from the system using its ID."
    )
    public ResponseEntity<String> removeRoute(@PathVariable Long route_id) {
        log.warn("[RouteController] Deleting route with ID={}", route_id);
        routeService.removeRoute(route_id);
        log.debug("[RouteController] Route {} deleted successfully", route_id);
        return new ResponseEntity<>("Route deleted successfully", HttpStatus.OK);
    }
}
