package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.entities.Passenger;
import com.dasvoximon.railwaysystem.repositories.PassengerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PassengerService {
    private PassengerRepository passengerRepository;

//    public Passenger addPassenger(PassengerRequest passengerRequest) {
//        return passengerRepository.save(passenger);
//    }
}
