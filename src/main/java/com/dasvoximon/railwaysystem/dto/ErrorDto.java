package com.dasvoximon.railwaysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDto {
    private int status;
    private String error;
    private String message;
}
