package com.dasvoximon.railwaysystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class ScheduleRequest {
    private Long routeId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private DayOfWeek operatingDay;
    private BigDecimal baseFare;
    private Integer seats;
}
