package com.dasvoximon.railwaysystem.model.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetailsDTO {
    //Schedule
    private Long scheduleId;
    private LocalDate travelDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private BigDecimal baseFare;
    private Integer seats;
    // Train
    private String trainName;
    private Integer capacity;
    // Origin Station
    private String originStation;
    private String originStationCity;
    private String originStationState;
    // Destination Station
    private String destinationStation;
    private String destinationStationCity;
    private String destinationStationState;
    // Route
    private BigDecimal distanceKm;
}
