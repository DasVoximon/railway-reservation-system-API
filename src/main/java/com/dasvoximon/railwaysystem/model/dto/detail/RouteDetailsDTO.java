package com.dasvoximon.railwaysystem.model.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RouteDetailsDTO {
    private Long routeId;
    private String trainName;
    private Integer capacity;
    private String originStation;
    private String originStationCity;
    private String originStationState;
    private String destinationStation;
    private String destinationStationCity;
    private String destinationStationState;
    private BigDecimal distanceKm;
}
