package com.dasvoximon.railwaysystem.wrapper;

import com.dasvoximon.railwaysystem.entities.Admin;
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
@XmlRootElement(name = "admins")
@XmlAccessorType(XmlAccessType.FIELD)
public class Admins {

    @XmlElement(name = "admin")
    private List<Admin> admins;
}
