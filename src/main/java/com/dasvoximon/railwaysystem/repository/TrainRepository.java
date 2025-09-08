package com.dasvoximon.railwaysystem.repository;

import com.dasvoximon.railwaysystem.model.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    Optional<Train> findByCode(String code);
    Boolean existsByCode(String code);
    void deleteByCode(String code);
}
