package com.dasvoximon.railwaysystem.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RouteRequest {
    private String trainCode;
    private String originStationCode;
    private String destinationStationCode;
    private BigDecimal distance;
}
