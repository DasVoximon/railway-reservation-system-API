package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.model.entity.sub.Name;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public void addPassenger(PassengerRegistrationRequest request) {
        log.info("Attempting to register passenger with email: {}", request.getEmail());

        Name name = new Name();
        name.setFirstName(request.getFirstName());
        name.setMiddleName(request.getMiddleName());
        name.setLastName(request.getLastName());

        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setEmail(request.getEmail());
        passenger.setPhone_number(request.getPhoneNumber());

        passengerRepository.save(passenger);
        log.info("Passenger registered successfully with email: {}", request.getEmail());
    }

    public List<Passenger> viewPassenger() {
        log.info("Fetching all passengers from database");
        List<Passenger> passengers = passengerRepository.findAll();
        if (passengers.isEmpty()) {
            log.error("No passengers found in database");
            throw new PassengerNotFoundException("No Passenger found in database");
        }
        log.info("Retrieved {} passengers from database", passengers.size());
        return passengers;
    }

    public void deletePassenger(Long passengerId) {
        log.info("Attempting to delete passenger with ID: {}", passengerId);
        if (!passengerRepository.existsById(passengerId)) {
            log.error("Passenger not found with ID: {}", passengerId);
            throw new PassengerNotFoundException("Passenger not found");
        }
        passengerRepository.deleteById(passengerId);
        log.info("Passenger with ID {} deleted successfully", passengerId);
    }
}
