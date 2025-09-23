package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
