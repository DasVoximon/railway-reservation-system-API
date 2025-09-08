package com.dasvoximon.railwaysystem.model.wrapper;

import com.dasvoximon.railwaysystem.model.entity.Schedule;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "schedules")
@XmlAccessorType(XmlAccessType.FIELD)
public class Schedules {

    @XmlElement(name = "schedule")
    public List<Schedule> schedules;
}
