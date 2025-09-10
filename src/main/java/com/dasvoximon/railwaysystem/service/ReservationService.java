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
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final EmailSender emailSender;

    // Book Ticket
    public void makeReservations(ReservationRequest reservationRequest) {
        Long scheduleId = reservationRequest.getScheduleId();
        String email = reservationRequest.getEmail();
        Integer seatNumber = reservationRequest.getSeatNumber();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        List<Integer> reservedSeats = reservationRepository.findReservedSeats(scheduleId);

        if (reservedSeats.contains(seatNumber)) {
            throw new SeatAlreadyTakenException("Seat " + seatNumber + " is already taken. " +
                    "Available seats: " + getAvailableSeats(schedule, reservedSeats));
        }

        Reservation reservation = new Reservation();
        reservation.setSchedule(schedule);
        reservation.setPassenger(passenger);
        reservation.setSeatNumber(seatNumber);
        reservation.setPnr(PnrGenerator.generate(8));

        reservationRepository.save(reservation);
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
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("No reservations in database");
        }
        return reservations;
    }

    // View Passenger's Ticket
    public List<Reservation> viewReservationsByPassenger(String email) {
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        List<Reservation> reservations = reservationRepository.findAllByPassengerOrderByIdDesc(passenger);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("You have made no reservations");
        }
        return reservations;
    }

    // View Passenger ticket by pnr
    public List<Reservation> viewReservationsByPassengerAndByPnr(String email, String pnr) {
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        List<Reservation> reservations = reservationRepository.findByPassengerAndPnrOrderByIdDesc(passenger, pnr);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation with pnr not found");
        }
        return reservations;
    }

    public void updateReservations(Long id, ReservationRequest reservationRequest) {
        Reservation updatedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        Long scheduleId = reservationRequest.getScheduleId();
        String email = reservationRequest.getEmail();
        Integer seatNumber = reservationRequest.getSeatNumber();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        updatedReservation.setSchedule(schedule);
        updatedReservation.setPassenger(passenger);
        updatedReservation.setSeatNumber(seatNumber);

        reservationRepository.save(updatedReservation);

    }

    public void deleteReservations(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }

    // Cancel Passenger's ticket by Pnr
    @Transactional
    public Reservation deleteReservationsByPnr(String pnr) {
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> new ReservationNotFoundException("Pnr not found"));

        if (reservation.getReservationStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationNotFoundException("Ticket has already been cancelled");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        return reservation;
    }

    // Generate Ticket Pdf
    public ByteArrayInputStream generateTicketPdf(String pnr) {
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> new ReservationNotFoundException("PNR not found"));

        return TicketPdfGenerator.generateTicket(reservation);
    }

    // Send Email Confirmation
    public String sendEmailConfirmation(String pnr) {
        Reservation reservation = reservationRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Reservation with PNR " + pnr + " not found"));

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

            return "Email sent successfully to " + reservation.getPassenger().getEmail();

        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

}
