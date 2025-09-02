package com.dasvoximon.railwaysystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private Long route_id;
    private LocalTime arrival_time;
    private LocalTime departure_time;
    private DayOfWeek operating_days;
    private BigDecimal base_fare;
    private Integer total_seats;
    private Integer available_seats;

}
