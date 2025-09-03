package com.dasvoximon.railwaysystem.dto;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RouteRequest {
    private Long trainId;
    private Long originStationId;
    private Long destinationStationId;
    private BigDecimal distance;
}
