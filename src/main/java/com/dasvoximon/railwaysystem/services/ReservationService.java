package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.dto.ReservationRequest;
import com.dasvoximon.railwaysystem.exceptions.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.ReservationNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.entities.Passenger;
import com.dasvoximon.railwaysystem.entities.Reservation;
import com.dasvoximon.railwaysystem.entities.Schedule;
import com.dasvoximon.railwaysystem.repositories.PassengerRepository;
import com.dasvoximon.railwaysystem.repositories.ReservationRepository;
import com.dasvoximon.railwaysystem.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;

    public void makeReservations(ReservationRequest reservationRequest) {
        Long scheduleId = reservationRequest.getScheduleId();
        Long passengerId = reservationRequest.getPassengerId();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        Reservation reservation = new Reservation();
        reservation.setSchedule(schedule);
        reservation.setPassenger(passenger);
        reservation.setBooked_at(reservationRequest.getBookedAt());
        reservation.setReservationStatus(reservationRequest.getReservationStatus());
        reservation.setSeat_no(reservationRequest.getSeatNo());
        reservation.setPnr(reservationRequest.getPnr());

        reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("You have made no reservations");
        }
        return reservations;
    }

    public void updateReservations(Long id, ReservationRequest reservationRequest) {
        Reservation updatedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        Long scheduleId = reservationRequest.getScheduleId();
        Long passengerId = reservationRequest.getPassengerId();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        updatedReservation.setSchedule(schedule);
        updatedReservation.setPassenger(passenger);
        updatedReservation.setBooked_at(reservationRequest.getBookedAt());
        updatedReservation.setReservationStatus(reservationRequest.getReservationStatus());
        updatedReservation.setSeat_no(reservationRequest.getSeatNo());
        updatedReservation.setPnr(reservationRequest.getPnr());

        reservationRepository.save(updatedReservation);

    }

    public void deleteReservations(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }


}
