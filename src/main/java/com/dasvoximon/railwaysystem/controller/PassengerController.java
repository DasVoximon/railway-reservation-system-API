package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.service.PassengerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<String> addPassenger(@Valid @RequestBody PassengerRegistrationRequest request) {
        passengerService.addPassenger(request);
        return new ResponseEntity<>("Passenger added", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getPassengers() {
        return new ResponseEntity<>(passengerService.viewPassenger(), HttpStatus.FOUND);
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return new ResponseEntity<>("Passenger deleted", HttpStatus.OK);
    }
}
