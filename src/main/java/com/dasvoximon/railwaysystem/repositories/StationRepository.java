package com.dasvoximon.railwaysystem.repositories;

import com.dasvoximon.railwaysystem.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByCode(String code);
    Optional<Station> findByCodeIgnoreCase(String code);
    Boolean existsByCode(String code);
    void deleteByCode(String code);
    Optional<Station> deleteByCodeIgnoreCase(String code);
}
