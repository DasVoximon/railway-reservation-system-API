package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.sub.ReservationStatus;
import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.exception.SeatAlreadyTakenException;
import com.dasvoximon.railwaysystem.exception.ReservationNotFoundException;
import com.dasvoximon.railwaysystem.exception.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import com.dasvoximon.railwaysystem.repository.ReservationRepository;
import com.dasvoximon.railwaysystem.repository.ScheduleRepository;
import com.dasvoximon.railwaysystem.util.EmailSender;
import com.dasvoximon.railwaysystem.util.PnrGenerator;
import com.dasvoximon.railwaysystem.util.TicketPdfGenerator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final EmailSender emailSender;

    // Book Ticket
    public void makeReservations(ReservationRequest reservationRequest) {
        log.info("Attempting to make reservation: {}", reservationRequest);

        Long scheduleId = reservationRequest.getScheduleId();
        String email = reservationRequest.getEmail();
        Integer seatNumber = reservationRequest.getSeatNumber();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    log.error("Schedule not found with ID {}", scheduleId);
                    return new ScheduleNotFoundException("Schedule not found");
                });
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Passenger not found with email {}", email);
                    return new PassengerNotFoundException("Passenger not found");
                });

        List<Integer> reservedSeats = reservationRepository.findReservedSeats(scheduleId);

        if (reservedSeats.contains(seatNumber)) {
            log.warn("Seat {} already taken for schedule ID {}. Reserved seats: {}", seatNumber, scheduleId, reservedSeats);
            throw new SeatAlreadyTakenException("Seat " + seatNumber + " is already taken. " +
                    "Available seats: " + getAvailableSeats(schedule, reservedSeats));
        }

        Reservation reservation = new Reservation();
        reservation.setSchedule(schedule);
        reservation.setPassenger(passenger);
        reservation.setSeatNumber(seatNumber);
        reservation.setPnr(PnrGenerator.generate(8));

        reservationRepository.save(reservation);
        log.info("Reservation successful. PNR: {}, Seat: {}, Passenger: {}", reservation.getPnr(), seatNumber, email);
    }

    private List<Integer> getAvailableSeats(Schedule schedule, List<Integer> reservedSeats) {
        int capacity = schedule.getRoute().getTrain().getCapacity();
        return java.util.stream.IntStream.rangeClosed(1, capacity)
                .filter(seat -> !reservedSeats.contains(seat))
                .boxed()
                .toList();
    }

    // View all Reservations in Database
    public List<Reservation> viewReservations() {
        log.info("Fetching all reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            log.error("No reservations found in database");
            throw new ReservationNotFoundException("No reservations in database");
        }
        log.info("Retrieved {} reservations", reservations.size());
        return reservations;
    }

    // View Passenger's Ticket
    public List<Reservation> viewReservationsByPassenger(String email) {
        log.info("Fetching reservations for passenger email: {}", email);
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Passenger not found with email {}", email);
                    return new PassengerNotFoundException("Passenger not found");
                });

        List<Reservation> reservations = reservationRepository.findAllByPassengerOrderByIdDesc(passenger);
        if (reservations.isEmpty()) {
            log.warn("No reservations found for passenger: {}", email);
            throw new ReservationNotFoundException("You have made no reservations");
        }
        log.info("Retrieved {} reservations for passenger {}", reservations.size(), email);
        return reservations;
    }

    // View Passenger ticket by pnr
    public List<Reservation> viewReservationsByPassengerAndByPnr(String email, String pnr) {
        log.info("Fetching reservations for passenger {} with PNR {}", email, pnr);
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Passenger not found with email {}", email);
                    return new PassengerNotFoundException("Passenger not found");
                });

        List<Reservation> reservations = reservationRepository.findByPassengerAndPnrOrderByIdDesc(passenger, pnr);
        if (reservations.isEmpty()) {
            log.warn("No reservation found for passenger {} with PNR {}", email, pnr);
            throw new ReservationNotFoundException("Reservation with pnr not found");
        }
        log.info("Retrieved {} reservations for passenger {} with PNR {}", reservations.size(), email, pnr);
        return reservations;
    }

    public void updateReservations(Long id, ReservationRequest reservationRequest) {
        log.info("Updating reservation with ID: {}", id);
        Reservation updatedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reservation not found with ID {}", id);
                    return new ReservationNotFoundException("Reservation not found");
                });

        Long scheduleId = reservationRequest.getScheduleId();
        String email = reservationRequest.getEmail();
        Integer seatNumber = reservationRequest.getSeatNumber();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    log.error("Schedule not found with ID {}", scheduleId);
                    return new ScheduleNotFoundException("Schedule not found");
                });
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Passenger not found with email {}", email);
                    return new PassengerNotFoundException("Passenger not found");
                });

        updatedReservation.setSchedule(schedule);
        updatedReservation.setPassenger(passenger);
        updatedReservation.setSeatNumber(seatNumber);

        reservationRepository.save(updatedReservation);
        log.info("Reservation with ID {} successfully updated", id);
    }

    public void deleteReservations(Long id) {
        log.info("Deleting reservation with ID: {}", id);
        if (!reservationRepository.existsById(id)) {
            log.error("Reservation not found with ID {}", id);
            throw new ReservationNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
        log.info("Reservation with ID {} deleted successfully", id);
    }

    // Cancel Passenger's ticket by Pnr
    @Transactional
    public Reservation deleteReservationsByPnr(String pnr) {
        log.info("Cancelling reservation with PNR: {}", pnr);
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> {
                    log.error("Reservation not found with PNR {}", pnr);
                    return new ReservationNotFoundException("Pnr not found");
                });

        if (reservation.getReservationStatus() == ReservationStatus.CANCELLED) {
            log.warn("Attempt to cancel already cancelled reservation with PNR {}", pnr);
            throw new ReservationNotFoundException("Ticket has already been cancelled");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        log.info("Reservation with PNR {} successfully cancelled", pnr);

        return reservation;
    }

    // Generate Ticket Pdf
    public ByteArrayInputStream generateTicketPdf(String pnr) {
        log.info("Generating ticket PDF for PNR {}", pnr);
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> {
                    log.error("Reservation not found with PNR {}", pnr);
                    return new ReservationNotFoundException("PNR not found");
                });

        log.info("Ticket PDF generated for PNR {}", pnr);
        return TicketPdfGenerator.generateTicket(reservation);
    }

    // Send Email Confirmation
    public String sendEmailConfirmation(String pnr) {
        log.info("Sending email confirmation for reservation with PNR {}", pnr);
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> {
                    log.error("Reservation not found with PNR {}", pnr);
                    return new RuntimeException("Reservation with PNR " + pnr + " not found");
                });

        try {
            ByteArrayInputStream pdfStream = TicketPdfGenerator.generateTicket(reservation);

            emailSender.sendTicketEmail(
                    reservation.getPassenger().getEmail(),
                    "Your Train Ticket Confirmation (PNR: " + reservation.getPnr() + ")",
                    "Dear " + reservation.getPassenger().getName().getFirstName()
                            + ",\n\nAttached is your train ticket.\nPNR: " + reservation.getPnr(),
                    pdfStream,
                    reservation.getPnr()
            );

            log.info("Email sent successfully to {}", reservation.getPassenger().getEmail());
            return "Email sent successfully to " + reservation.getPassenger().getEmail();

        } catch (MessagingException e) {
            log.error("Failed to send email for PNR {}. Error: {}", pnr, e.getMessage(), e);
            return "Failed to send email: " + e.getMessage();
        }
    }

}
