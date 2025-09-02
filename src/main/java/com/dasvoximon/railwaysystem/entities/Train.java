package com.dasvoximon.railwaysystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "trains_seq"
    )
    @SequenceGenerator(
            name = "trains_seq",
            sequenceName = "trains_seq",
            allocationSize = 1
    )
    private long train_id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Train should have a name")
    private String train_name;

    @Min(1)
    private int capacity;

//    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
//    private List<Route> routes = new ArrayList<>();
}
