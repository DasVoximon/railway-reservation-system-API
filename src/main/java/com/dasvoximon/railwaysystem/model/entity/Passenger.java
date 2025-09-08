package com.dasvoximon.railwaysystem.model.entity;

import com.dasvoximon.railwaysystem.model.entity.sub.Name;
import com.dasvoximon.railwaysystem.model.entity.sub.Role;
import com.dasvoximon.railwaysystem.model.entity.sub.ValidPhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
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
