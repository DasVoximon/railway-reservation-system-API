package com.dasvoximon.railwaysystem.entities;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "routes_seq"
    )
    @SequenceGenerator(
            name = "routes_seq",
            sequenceName = "routes_seq",
            allocationSize = 1
    )
    @Column(name = "route_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "origin_station_id")
    private Station originStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id")
    private Station destinationStation;

    @NotNull
    @Positive(message = "Distance can not be negative")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "distance_in_km", precision = 12, scale = 2)
    private BigDecimal distance;

}
