package com.dasvoximon.railwaysystem.model.wrapper;

import com.dasvoximon.railwaysystem.model.entity.Reservation;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "reservations")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reservations {

    @XmlElement(name = "reservations")
    private List<Reservation> reservations;
}
