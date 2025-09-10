package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.PassengerNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.model.entity.sub.Name;
import com.dasvoximon.railwaysystem.repository.PassengerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PassengerService {
    private PassengerRepository passengerRepository;

    public Passenger addPassenger(PassengerRegistrationRequest request) {
        Name name = new Name();
        name.setFirstName(request.getFirstName());
        name.setMiddleName(request.getMiddleName());
        name.setLastName(request.getLastName());

        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setEmail(request.getEmail());
        passenger.setPhone_number(request.getPhoneNumber());

        return passengerRepository.save(passenger);
    }

    public List<Passenger> viewPassenger() {
        List<Passenger> passengers = passengerRepository.findAll();
        if (passengers.isEmpty()) {
            throw new PassengerNotFoundException("No Passenger found in database");
        }
        return passengers;
    }

    public void deletePassenger(Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new PassengerNotFoundException("Passenger not found");
        }
        passengerRepository.deleteById(passengerId);
    }
}
