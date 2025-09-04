package com.dasvoximon.railwaysystem.controllers;

import com.dasvoximon.railwaysystem.dto.CancelTicketRequest;
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
    public ResponseEntity<String> bookTicket(@Valid @RequestBody ReservationRequest reservationRequest) {
        reservationService.makeReservations(reservationRequest);
        return new ResponseEntity<>("Ticket booked successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> viewReservations() {
        return new ResponseEntity<>(reservationService.viewReservations(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Reservation>> viewPassengerTicket(@PathVariable @Valid String email) {
        return new ResponseEntity<>(reservationService.viewReservationsByPassenger(email), HttpStatus.FOUND);
    }

    @GetMapping("/{email}/{pnr}")
    public ResponseEntity<List<Reservation>> viewPassengerTicketByPnr(@PathVariable @Valid String email,
                                                                      @PathVariable @Valid String pnr) {
        return new ResponseEntity<>(reservationService.viewReservationsByPassengerAndByPnr(email, pnr), HttpStatus.FOUND);
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

    @DeleteMapping("/{pnr}")
    public ResponseEntity<CancelTicketRequest> cancelPassengerTicketByPnr(@PathVariable String pnr) {
        return new ResponseEntity<>(reservationService.deleteReservationsByPnr(pnr), HttpStatus.valueOf("CANCELLED"));
    }

}
