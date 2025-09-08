package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByCode(String code);
    Boolean existsByCode(String code);
    void deleteByCode(String code);
}
