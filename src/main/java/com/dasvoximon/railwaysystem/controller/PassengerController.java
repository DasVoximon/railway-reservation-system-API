package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.service.PassengerService;
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
@RequestMapping("/api/passengers")
@Tag(
        name = "Passenger API",
        description = "Endpoints for managing passengers: registration, listing, and deletion"
)
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    @Operation(
            summary = "Register Passenger",
            description = "Register a new passenger by providing the required details such as name, email, phone number, and password."
    )
    public ResponseEntity<String> addPassenger(@Valid @RequestBody PassengerRegistrationRequest request) {
        log.info("Registering new passenger with email: {}", request.getEmail());
        passengerService.addPassenger(request);
        log.info("Passenger with email {} registered successfully", request.getEmail());
        return new ResponseEntity<>("Passenger added", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Passengers",
            description = "Retrieve a list of all passengers registered in the system."
    )
    public ResponseEntity<List<Passenger>> getPassengers() {
        log.info("Fetching all passengers");
        List<Passenger> passengers = passengerService.viewPassenger();
        log.info("Retrieved {} passengers", passengers.size());
        return new ResponseEntity<>(passengers, HttpStatus.FOUND);
    }

    @DeleteMapping("/{passengerId}")
    @Operation(
            summary = "Delete Passenger",
            description = "Delete a passenger from the system using their unique ID."
    )
    public ResponseEntity<String> deletePassenger(@PathVariable Long passengerId) {
        log.warn("Deleting passenger with ID {}", passengerId);
        passengerService.deletePassenger(passengerId);
        log.info("Passenger with ID {} deleted successfully", passengerId);
        return new ResponseEntity<>("Passenger deleted", HttpStatus.OK);
    }
}
