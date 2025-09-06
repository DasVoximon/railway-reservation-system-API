package com.dasvoximon.railwaysystem.dto.request;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CancelTicketRequest {
    private String pnr;
    private ReservationStatus reservationStatus;
    private LocalDateTime cancelledAt;
}
