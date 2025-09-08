package com.dasvoximon.railwaysystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "stations_seq"
    )
    @SequenceGenerator(
            name = "stations_seq",
            sequenceName = "stations_seq",
            allocationSize = 1
    )
    @Column(name = "station_id")
    private long id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Station should have a name")
    @Column(name = "station_name")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

}
