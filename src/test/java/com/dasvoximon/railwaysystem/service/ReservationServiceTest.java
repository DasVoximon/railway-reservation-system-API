package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.exception.ReservationNotFoundException;
import com.dasvoximon.railwaysystem.exception.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import com.dasvoximon.railwaysystem.repository.ReservationRepository;
import com.dasvoximon.railwaysystem.repository.ScheduleRepository;
import com.dasvoximon.railwaysystem.util.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private PassengerRepository passengerRepository;
    @Mock private EmailSender emailSender;

    @InjectMocks private ReservationService reservationService;

    private ReservationRequest request;
    private Passenger passenger;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new ReservationRequest(1L, "john@example.com", 5);

        passenger = new Passenger();
        passenger.setEmail("john@example.com");

        schedule = new Schedule();
        schedule.setId(1L);
    }

    @Test
    void makeReservations_ShouldSave_WhenValid() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(passengerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(passenger));
        when(reservationRepository.findReservedSeats(1L)).thenReturn(Collections.emptyList());

        reservationService.makeReservations(request);

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void makeReservations_ShouldThrow_WhenScheduleNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.makeReservations(request))
                .isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    void viewReservations_ShouldThrow_WhenEmpty() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> reservationService.viewReservations())
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("No reservations in database");
    }

    @Test
    void viewReservationsByPassenger_ShouldThrow_WhenPassengerNotFound() {
        when(passengerRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.viewReservationsByPassenger("john@example.com"))
                .isInstanceOf(PassengerNotFoundException.class);
    }
}
