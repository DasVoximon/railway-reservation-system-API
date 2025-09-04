package com.dasvoximon.railwaysystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RouteRequest {
    private Long trainId;
    private Long originStationId;
    private Long destinationStationId;
    private BigDecimal distance;
}
