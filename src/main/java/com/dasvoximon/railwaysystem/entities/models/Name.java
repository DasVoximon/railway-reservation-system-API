package com.dasvoximon.railwaysystem.entities.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Name {

    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Middle name must contain only letters")
    private String middleName;

    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    @NotBlank(message = "Last name is required")
    private String lastName;
}
