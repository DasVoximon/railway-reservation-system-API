package com.dasvoximon.railwaysystem.dto;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long scheduleId;
    private String email;
    private Integer seatNumber;
}
