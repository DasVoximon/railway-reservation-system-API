package com.dasvoximon.railwaysystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "train_id")
    private long id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Train should have a name")
    @Column(name = "train_name")
    private String name;

    @Min(1)
    @NotNull(message = "Input the maximum capacity of train")
    private int capacity;
}
