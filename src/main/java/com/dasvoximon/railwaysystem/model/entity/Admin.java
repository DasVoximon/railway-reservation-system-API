package com.dasvoximon.railwaysystem.model.entity;

import com.dasvoximon.railwaysystem.model.entity.sub.Name;
import com.dasvoximon.railwaysystem.model.entity.sub.Role;
import com.dasvoximon.railwaysystem.model.entity.sub.ValidPhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "admin_seq"
    )
    @SequenceGenerator(
            name = "admin_seq",
            sequenceName = "admin_seq",
            allocationSize = 1
    )
    @Column(name = "admin_id")
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
    private Role role = Role.ADMIN;
}
