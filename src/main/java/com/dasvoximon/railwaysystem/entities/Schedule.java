package com.dasvoximon.railwaysystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "schedule_seq"
    )
    @SequenceGenerator(
            name = "schedule_seq",
            sequenceName = "schedule_seq",
            allocationSize = 1
    )
    @Column(name = "schedule_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    @NotNull
    private DayOfWeek operatingDay;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 12, scale = 2)
    private BigDecimal base_fare;

    @NotNull
    @Positive
    private int seats;

}
