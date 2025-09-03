package com.dasvoximon.railwaysystem.entities;

import com.dasvoximon.railwaysystem.entities.models.Name;
import com.dasvoximon.railwaysystem.entities.models.Role;
import com.dasvoximon.railwaysystem.entities.models.ValidPhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "passengers",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Passenger {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "passenger_seq"
    )
    @SequenceGenerator(
            name = "passenger_seq",
            sequenceName = "passenger_seq",
            allocationSize = 1
    )
    @Column(name = "passenger_id")
    private long id;

    @Embedded
    @Valid
    @NotNull
    private Name name;

    @NotBlank
    @Email(message = "Must be in proper email format e.g.: johndoe@gmail.com")
    private String email;

    @ValidPhoneNumber
    private String phone_number;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.PASSENGER;
}
