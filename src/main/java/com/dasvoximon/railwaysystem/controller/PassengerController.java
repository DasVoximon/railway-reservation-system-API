package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.service.PassengerService;
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
@RequestMapping("/api/passengers")
@Tag(
        name = "Passenger API",
        description = "Endpoints for managing passengers: registration, listing, and deletion"
)
public class PassengerController {

    private PassengerService passengerService;

    @PostMapping
    @Operation(
            summary = "Register Passenger",
            description = "Register a new passenger by providing the required details such as name, email, phone number, and password."
    )
    public ResponseEntity<String> addPassenger(@Valid @RequestBody PassengerRegistrationRequest request) {
        passengerService.addPassenger(request);
        return new ResponseEntity<>("Passenger added", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Passengers",
            description = "Retrieve a list of all passengers registered in the system."
    )
    public ResponseEntity<List<Passenger>> getPassengers() {
        return new ResponseEntity<>(passengerService.viewPassenger(), HttpStatus.FOUND);
    }

    @DeleteMapping("/{passengerId}")
    @Operation(
            summary = "Delete Passenger",
            description = "Delete a passenger from the system using their unique ID."
    )
    public ResponseEntity<String> deletePassenger(@PathVariable Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return new ResponseEntity<>("Passenger deleted", HttpStatus.OK);
    }
}
