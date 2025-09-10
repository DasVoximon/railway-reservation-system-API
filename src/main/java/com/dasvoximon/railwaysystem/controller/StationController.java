package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stations")
@Tag(
        name = "Station API",
        description = "Endpoints for managing railway stations: create, update, fetch, and delete stations"
)
public class StationController {

    private final StationService stationService;

    @PostMapping
    @Operation(
            summary = "Add Station",
            description = "Add a single station to the system. Requires station details such as code and name."
    )
    public ResponseEntity<String> addStation(@Valid @RequestBody Station station) {
        stationService.addStation(station);
        return new ResponseEntity<>("Station added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(
            summary = "Add Multiple Stations",
            description = "Add multiple stations at once by providing a list of station objects."
    )
    public ResponseEntity<String> addStations(@Valid @RequestBody List<Station> stations) {
        stationService.addStations(stations);
        return new ResponseEntity<>("Stations added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Stations",
            description = "Retrieve a list of all stations available in the system."
    )
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }

    @GetMapping("/{code}")
    @Operation(
            summary = "Get Station by Code",
            description = "Fetch the details of a specific station using its unique code."
    )
    public ResponseEntity<Station> getStationByCode(@PathVariable String code) {
        return ResponseEntity.ok(stationService.getStationByCode(code));
    }

    @PutMapping("/{code}")
    @Operation(
            summary = "Update Station",
            description = "Update an existing station record using its unique code. Provide updated station details in the request body."
    )
    public ResponseEntity<String> updateStation(@PathVariable String code, @Valid @RequestBody Station station) {
        stationService.updateStation(code, station);
        return new ResponseEntity<>("Station updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    @Operation(
            summary = "Delete Station",
            description = "Remove a station permanently from the system using its unique code."
    )
    public ResponseEntity<String> deleteStation(@PathVariable String code) {
        stationService.removeStation(code);
        return new ResponseEntity<>("Station deleted successfully", HttpStatus.OK);
    }
}
