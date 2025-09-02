package com.dasvoximon.railwaysystem.controllers;

import com.dasvoximon.railwaysystem.dto.ReservationRequest;
import com.dasvoximon.railwaysystem.entities.Reservation;
import com.dasvoximon.railwaysystem.services.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> makeReservations(@Valid @RequestBody ReservationRequest reservationRequest) {
        reservationService.makeReservations(reservationRequest);
        return new ResponseEntity<>("Reservations made", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservationService.getReservations(), HttpStatus.FOUND);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservations(@PathVariable Long reservationId,
                                                     @Valid @RequestBody ReservationRequest reservationRequest) {
        reservationService.updateReservations(reservationId, reservationRequest);
        return new ResponseEntity<>("Reservations updated", HttpStatus.valueOf("UPDATED"));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservations(reservationId);
        return new ResponseEntity<>("Reservations deleted", HttpStatus.valueOf("DELETED"));
    }


}
