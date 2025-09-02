package com.dasvoximon.railwaysystem.entities;

import com.dasvoximon.railwaysystem.entities.models.Name;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// @Entity
// @Table(name = "admins")
public class Admin {

    // @Id
    private long admin_id;
    private Name name;
    private String username;
    private String email;
}
