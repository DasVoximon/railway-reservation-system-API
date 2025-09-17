package com.dasvoximon.railwaysystem.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RouteRequest {
    private String trainCode;
    private String originStationCode;
    private String destinationStationCode;
    private BigDecimal distance;
}
