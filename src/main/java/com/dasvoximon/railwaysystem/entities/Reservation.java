package com.dasvoximon.railwaysystem.entities;

import com.dasvoximon.railwaysystem.entities.models.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "reservation_seq"
    )
    @SequenceGenerator(
            name = "reservation_seq",
            sequenceName = "reservation_seq",
            allocationSize = 1
    )
    @Column(name = "reservation_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime bookedAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = ReservationStatus.BOOKED;

    @Column(unique = true)
    private String seatNumber;

    @Column(unique = true, nullable = false)
    private String pnr;

}
