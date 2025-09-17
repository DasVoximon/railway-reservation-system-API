package com.dasvoximon.railwaysystem.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleRequest {
    private Long routeId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private LocalDate travelDate;
    private BigDecimal baseFare;
    private Integer seats;
}
