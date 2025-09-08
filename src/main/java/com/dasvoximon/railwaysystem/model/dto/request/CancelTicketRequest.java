package com.dasvoximon.railwaysystem.model.dto.request;

import com.dasvoximon.railwaysystem.model.entity.sub.ReservationStatus;
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
