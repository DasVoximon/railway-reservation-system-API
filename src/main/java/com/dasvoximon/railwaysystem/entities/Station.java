package com.dasvoximon.railwaysystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    private long station_id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Station should have a name")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

//    @OneToMany(mappedBy = "originStation", cascade = CascadeType.ALL)
//    private List<Route> origin = new ArrayList<>();
//
//    @OneToMany(mappedBy = "destinationStation", cascade = CascadeType.ALL)
//    private List<Route> destination = new ArrayList<>();

}
