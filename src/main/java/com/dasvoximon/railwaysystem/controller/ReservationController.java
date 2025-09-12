package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
@Tag(
        name = "Reservation API",
        description = "Endpoints for booking, viewing, updating, and cancelling train reservations"
)
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(
            summary = "Book Ticket",
            description = "Create a new reservation for a passenger on a given schedule. " +
                    "Requires passenger email, schedule ID, and seat number."
    )
    public ResponseEntity<String> bookTicket(@Valid @RequestBody ReservationRequest reservationRequest) {
        log.info("Booking ticket for passenger with email: {}", reservationRequest.getEmail());
        reservationService.makeReservations(reservationRequest);
        log.debug("Reservation details: {}", reservationRequest);
        return new ResponseEntity<>("Ticket booked", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "View All Reservations",
            description = "Fetch a list of all reservations currently in the database."
    )
    public ResponseEntity<List<Reservation>> viewReservations() {
        log.info("Fetching all reservations");
        return new ResponseEntity<>(reservationService.viewReservations(), HttpStatus.FOUND);
    }

    @GetMapping("/ticket/{email}")
    @Operation(
            summary = "View Passenger's Tickets",
            description = "Fetch all reservations belonging to a passenger using their email address."
    )
    public ResponseEntity<List<Reservation>> viewPassengerTicket(@PathVariable @Valid String email) {
        log.info("Fetching tickets for passenger with email: {}", email);
        return new ResponseEntity<>(reservationService.viewReservationsByPassenger(email), HttpStatus.FOUND);
    }

    @GetMapping("/ticket/{email}/{pnr}")
    @Operation(
            summary = "View Passenger Ticket by PNR",
            description = "Fetch a passenger’s reservation(s) using their email and PNR number."
    )
    public ResponseEntity<List<Reservation>> viewPassengerTicketByPnr(
            @PathVariable @Email(message = "format = user@example.com") String email,
            @PathVariable String pnr) {
        log.info("Fetching ticket for passenger with email: {} and PNR: {}", email, pnr);
        return new ResponseEntity<>(reservationService.viewReservationsByPassengerAndByPnr(email, pnr), HttpStatus.FOUND);
    }

    @PutMapping("/{reservationId}")
    @Operation(
            summary = "Update Reservation",
            description = "Update an existing reservation using its ID. " +
                    "Provide passenger email, schedule ID, and new seat number."
    )
    public ResponseEntity<String> updateReservations(@PathVariable Long reservationId,
                                                     @Valid @RequestBody ReservationRequest reservationRequest) {
        log.info("Updating reservation with ID: {}", reservationId);
        reservationService.updateReservations(reservationId, reservationRequest);
        log.debug("Updated reservation details: {}", reservationRequest);
        return new ResponseEntity<>("Reservations updated", HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    @Operation(
            summary = "Delete Reservation",
            description = "Delete a reservation permanently from the database using its ID."
    )
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        log.warn("Deleting reservation with ID: {}", reservationId);
        reservationService.deleteReservations(reservationId);
        return new ResponseEntity<>("Reservations deleted", HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{pnr}")
    @Operation(
            summary = "Cancel Ticket by PNR",
            description = "Cancel a passenger’s reservation by its PNR. " +
                    "Changes reservation status to CANCELLED instead of deleting it."
    )
    public ResponseEntity<Reservation> cancelPassengerTicketByPnr(@PathVariable String pnr) {
        log.warn("Cancelling reservation with PNR: {}", pnr);
        return new ResponseEntity<>(reservationService.deleteReservationsByPnr(pnr), HttpStatus.OK);
    }

    @GetMapping("/ticket/{pnr}/pdf")
    @Operation(
            summary = "Download Ticket PDF",
            description = "Generate and download a passenger’s train ticket in PDF format using PNR."
    )
    public ResponseEntity<InputStreamResource> downloadTicket(@PathVariable String pnr) {
        log.info("Generating PDF ticket for PNR: {}", pnr);
        ByteArrayInputStream bis = reservationService.generateTicketPdf(pnr);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticket_" + pnr + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/ticket/{pnr}/email")
    @Operation(
            summary = "Send Ticket by Email",
            description = "Generate a passenger’s ticket as a PDF and send it via email using PNR."
    )
    public ResponseEntity<String> sendEmailConfirmation(@PathVariable String pnr) {
        log.info("Sending email confirmation for PNR: {}", pnr);
        return new ResponseEntity<>(reservationService.sendEmailConfirmation(pnr), HttpStatus.FOUND);
    }
}
