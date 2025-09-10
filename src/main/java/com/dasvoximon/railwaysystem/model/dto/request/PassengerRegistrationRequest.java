package com.dasvoximon.railwaysystem.model.dto.request;

import lombok.Data;

@Data
public class PassengerRegistrationRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
