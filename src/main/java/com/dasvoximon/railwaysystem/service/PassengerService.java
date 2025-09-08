package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.repository.PassengerRepository;
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
