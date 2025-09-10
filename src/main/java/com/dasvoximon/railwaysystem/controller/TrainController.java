package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.service.TrainService;
import com.dasvoximon.railwaysystem.model.wrapper.Trains;
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
@RequestMapping("/api/trains")
@Tag(
        name = "Train API",
        description = "Endpoints for managing trains: create, update, fetch, and delete train records"
)
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    @Operation(
            summary = "Add Train",
            description = "Add a single train to the system. Requires train details such as code, name, and capacity."
    )
    public ResponseEntity<String> addTrain(@Valid @RequestBody Train train) {
        trainService.addTrain(train);
        return new ResponseEntity<>("Train added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(
            summary = "Add Multiple Trains",
            description = "Add multiple trains at once by passing a list of train objects."
    )
    public ResponseEntity<String> addTrains(@Valid @RequestBody List<Train> trains) {
        trainService.addTrains(trains);
        return new ResponseEntity<>("Trains added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Trains",
            description = "Retrieve a list of all trains available in the system. Results are wrapped in a `Trains` object."
    )
    public ResponseEntity<Trains> getTrains() {
        List<Train> trains = trainService.getTrains();
        Trains trainWrapper = new Trains(trains);
        return new ResponseEntity<>(trainWrapper, HttpStatus.FOUND);
    }

    @GetMapping("/{code}")
    @Operation(
            summary = "Get Train by Code",
            description = "Fetch details of a specific train using its unique code."
    )
    public ResponseEntity<Train> getTrainByCode(@PathVariable String code) {
        return new ResponseEntity<>(trainService.getTrainByCode(code), HttpStatus.FOUND);
    }

    @PutMapping("/{code}")
    @Operation(
            summary = "Update Train",
            description = "Update the details of a train using its unique code. Provide updated train information in the request body."
    )
    public ResponseEntity<String> updateTrain(@PathVariable String code,
                                              @Valid @RequestBody Train train) {
        trainService.updateTrain(code, train);
        return new ResponseEntity<>("Train updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    @Operation(
            summary = "Delete Train",
            description = "Remove a train from the system permanently using its unique code."
    )
    public ResponseEntity<String> removeTrain(@PathVariable String code) {
        trainService.removeTrain(code);
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
    }
}
