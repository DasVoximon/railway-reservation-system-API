package com.dasvoximon.railwaysystem.model.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRequest {
    private Long scheduleId;
    @Email(message = "must be in format name@gmail.com")
    private String email;
    private Integer seatNumber;
}
