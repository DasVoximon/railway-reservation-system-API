package com.dasvoximon.railwaysystem.controllers;

import com.dasvoximon.railwaysystem.entities.Station;
import com.dasvoximon.railwaysystem.services.StationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stations")
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<String> addStation(@Valid @RequestBody Station station) {
        stationService.addStation(station);
        String msg = "Station added successfully";
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addStations(@Valid @RequestBody List<Station> stations) {
        stationService.addStations(stations);
        String message = "Stations added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Station> getStationByCode(@PathVariable String code) {
        return ResponseEntity.ok(stationService.getStationByCode(code));
    }

//    @GetMapping("/{code}")
//    public ResponseEntity<Station> getStationByCodeIgnoreCase(@PathVariable String code) {
//        return ResponseEntity.ok(adminService.getStationByCodeIgnoreCase(code));
//    }

    @PutMapping("/{code}")
    public ResponseEntity<String> updateStation(@PathVariable String code, @Valid @RequestBody Station station) {
        stationService.updateStation(code, station);
        String msg = "Station updated successfully";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteStation(@PathVariable String code) {
        stationService.removeStation(code);
        String message = "Station deleted succefully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
