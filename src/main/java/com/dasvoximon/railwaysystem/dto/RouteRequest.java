package com.dasvoximon.railwaysystem.dto;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RouteRequest {
    private Long train_id;
    private Long origin_station_id;
    private Long destination_station_id;
    private BigDecimal distance_km;
    private ReservationStatus reservationStatus;
}
