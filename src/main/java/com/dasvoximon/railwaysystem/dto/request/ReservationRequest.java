package com.dasvoximon.railwaysystem.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ReservationRequest {
    private Long scheduleId;
    @Email(message = "must be in format name@gmail.com")
    private String email;
    private Integer seatNumber;
}
