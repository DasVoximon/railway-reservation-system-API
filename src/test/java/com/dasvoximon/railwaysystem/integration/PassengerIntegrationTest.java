package com.dasvoximon.railwaysystem.integration;

import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import com.dasvoximon.railwaysystem.service.PassengerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional // rollback after each test
class PassengerIntegrationTest {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PassengerRepository passengerRepository;

    private PassengerRegistrationRequest request;

    @BeforeEach
    void setUp() {
        request = new PassengerRegistrationRequest();
        request.setFirstName("John");
        request.setMiddleName("Doe");
        request.setLastName("Smith");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumber("09025056267");
    }

    @Test
    void testAddPassenger_ShouldSaveToDatabase() {
        // when
        passengerService.addPassenger(request);

        // then
        Passenger passenger = passengerRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new AssertionError("Passenger not found"));

        assertThat(passenger.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testViewPassenger_ShouldReturnPassengers() {
        // given
        passengerService.addPassenger(request);

        // when
        List<Passenger> passengers = passengerService.viewPassenger();

        // then
        Passenger passenger = passengerRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new AssertionError("Passenger not found"));

        assertThat(passengers).hasSize(passengers.size());
        assertThat(passenger.getName().getFirstName()).isEqualTo("John");
    }

    @Test
    void testViewPassenger_WhenEmpty_ShouldThrowException() {
        // skip test if passenger table is not empty
        assumeTrue(passengerRepository.count() == 0,
                "Skipping test because passengers exist in DB");

        // expect
        assertThatThrownBy(() -> passengerService.viewPassenger())
                .isInstanceOf(PassengerNotFoundException.class)
                .hasMessage("No Passenger found in database");
    }

    @Test
    void testDeletePassenger_ShouldRemoveFromDatabase() {
        // given
        passengerService.addPassenger(request);
        Passenger passenger = passengerRepository.findAll().getFirst();

        // when
        passengerService.deletePassenger(passenger.getId());

        // then
        assertThat(passengerRepository.findById(passenger.getId())).isEmpty();
    }

    @Test
    void testDeletePassenger_WhenNotFound_ShouldThrowException() {
        // expect
        assertThatThrownBy(() -> passengerService.deletePassenger(999L))
                .isInstanceOf(PassengerNotFoundException.class)
                .hasMessage("Passenger not found");
    }
}
