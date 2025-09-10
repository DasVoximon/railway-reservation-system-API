package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
@Tag(
        name = "Reservation API",
        description = "\nManage Train Reservations"
)
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(
            summary = "Book Ticket/Create Reservations"
    )
    public ResponseEntity<String> bookTicket(@Valid @RequestBody ReservationRequest reservationRequest) {
        reservationService.makeReservations(reservationRequest);
        return new ResponseEntity<>("Ticket booked", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> viewReservations() {
        return new ResponseEntity<>(reservationService.viewReservations(), HttpStatus.FOUND);
    }

    @GetMapping("/ticket/{email}")
    public ResponseEntity<List<Reservation>> viewPassengerTicket(@PathVariable @Valid String email) {
        return new ResponseEntity<>(reservationService.viewReservationsByPassenger(email), HttpStatus.FOUND);
    }

    @GetMapping("/ticket/{email}/{pnr}")
    public ResponseEntity<List<Reservation>> viewPassengerTicketByPnr(@PathVariable @Email(message = "format = user@example.com") String email,
                                                                      @PathVariable String pnr) {
        return new ResponseEntity<>(reservationService.viewReservationsByPassengerAndByPnr(email, pnr), HttpStatus.FOUND);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservations(@PathVariable Long reservationId,
                                                     @Valid @RequestBody ReservationRequest reservationRequest) {
        reservationService.updateReservations(reservationId, reservationRequest);
        return new ResponseEntity<>("Reservations updated", HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservations(reservationId);
        return new ResponseEntity<>("Reservations deleted", HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{pnr}")
    public ResponseEntity<Reservation> cancelPassengerTicketByPnr(@PathVariable String pnr) {
        return new ResponseEntity<>(reservationService.deleteReservationsByPnr(pnr), HttpStatus.OK);
    }

    @GetMapping("/ticket/{pnr}/pdf")
    public ResponseEntity<InputStreamResource> downloadTicket(@PathVariable String pnr) {
        ByteArrayInputStream bis = reservationService.generateTicketPdf(pnr);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticket_" + pnr + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/ticket/{pnr}/email")
    public ResponseEntity<String> sendEmailConfirmation(@PathVariable String pnr) {
        return new ResponseEntity<>(reservationService.sendEmailConfirmation(pnr), HttpStatus.FOUND);
    }

}
