package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.entity.Users;
import com.dasvoximon.railwaysystem.model.entity.sub.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    String findUserByUsername(String username);

    List<Users> findUsersByRole(Role role);
}
