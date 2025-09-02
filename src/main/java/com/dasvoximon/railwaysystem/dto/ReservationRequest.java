package com.dasvoximon.railwaysystem.dto;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ReservationRequest {
    private Long scheduleId;
    private Long passengerId;
    private LocalTime bookedAt;
    private ReservationStatus reservationStatus;
    private String seatNo;
    private String pnr;
}
