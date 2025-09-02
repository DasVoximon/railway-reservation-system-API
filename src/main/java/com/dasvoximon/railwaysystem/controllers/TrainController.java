package com.dasvoximon.railwaysystem.controllers;

import com.dasvoximon.railwaysystem.entities.Train;
import com.dasvoximon.railwaysystem.services.TrainService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/trains")
public class TrainController {

    private TrainService trainService;

    @PostMapping
    public ResponseEntity<String> addTrain(@Valid @RequestBody Train train) {
        trainService.addTrain(train);
        String message = "Train added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addTrains(@Valid @RequestBody List<Train> trains) {
        trainService.addTrains(trains);
        return new ResponseEntity<>("Trains added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Train>> getTrains() {
        return new ResponseEntity<>(trainService.getTrains(), HttpStatus.FOUND);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Train> getTrainByCode(@PathVariable String code) {
        return new ResponseEntity<>(trainService.getTrainByCode(code), HttpStatus.FOUND);
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> updateTrain(@PathVariable String code, @Valid @RequestBody Train train) {
        trainService.updateTrain(code, train);
        String message = "Train updated successfully";
        return new ResponseEntity<>(message, HttpStatus.valueOf("Updated"));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> removeTrain(@PathVariable String code) {
        trainService.removeTrain(code);
        String message = "Train deleted successfully";
        return new ResponseEntity<>(message, HttpStatus.valueOf("Deleted"));
    }
}
