package com.dasvoximon.railwaysystem.wrapper;

import com.dasvoximon.railwaysystem.entities.Passenger;
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
@XmlRootElement(name = "passengers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Passengers {

    @XmlElement(name = "passenger")
    private List<Passenger> passengers;
}
