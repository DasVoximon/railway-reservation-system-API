package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPassenger_ShouldSavePassenger() {
        PassengerRegistrationRequest request = new PassengerRegistrationRequest(
                "John", "M", "Doe", "john@example.com", "123456789"
        );

        passengerService.addPassenger(request);

        ArgumentCaptor<Passenger> passengerCaptor = ArgumentCaptor.forClass(Passenger.class);
        verify(passengerRepository, times(1)).save(passengerCaptor.capture());

        Passenger savedPassenger = passengerCaptor.getValue();
        assertThat(savedPassenger.getEmail()).isEqualTo("john@example.com");
        assertThat(savedPassenger.getName().getFirstName()).isEqualTo("John");
    }

    @Test
    void viewPassenger_ShouldReturnPassengers_WhenFound() {
        Passenger passenger = new Passenger();
        passenger.setEmail("jane@example.com");
        when(passengerRepository.findAll()).thenReturn(List.of(passenger));

        List<Passenger> result = passengerService.viewPassenger();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    void viewPassenger_ShouldThrow_WhenNoPassengersFound() {
        when(passengerRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> passengerService.viewPassenger())
                .isInstanceOf(PassengerNotFoundException.class)
                .hasMessage("No Passenger found in database");
    }

    @Test
    void deletePassenger_ShouldDelete_WhenPassengerExists() {
        Long passengerId = 1L;
        when(passengerRepository.existsById(passengerId)).thenReturn(true);

        passengerService.deletePassenger(passengerId);

        verify(passengerRepository, times(1)).deleteById(passengerId);
    }

    @Test
    void deletePassenger_ShouldThrow_WhenPassengerDoesNotExist() {
        Long passengerId = 1L;
        when(passengerRepository.existsById(passengerId)).thenReturn(false);

        assertThatThrownBy(() -> passengerService.deletePassenger(passengerId))
                .isInstanceOf(PassengerNotFoundException.class)
                .hasMessage("Passenger not found");
    }
}
