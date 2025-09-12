package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.StationNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.repository.StationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class StationService {

    private final StationRepository stationRepository;

    public void addStation(Station station) {
        log.info("Adding new station: {}", station.getName());
        if (station.getCode() == null || station.getCode().isBlank()) {
            String generatedCode = generateUniqueCode(station.getName());
            station.setCode(generatedCode);
            log.debug("Generated unique code [{}] for station [{}]", generatedCode, station.getName());
        }
        stationRepository.save(station);
        log.info("Station [{}] saved successfully", station.getCode());
    }

    public void addStations(List<Station> stations) {
        log.info("Adding {} stations", stations.size());
        for (Station station : stations) {
            if (station.getCode() == null || station.getCode().isBlank()) {
                String generatedCode = generateUniqueCode(station.getName());
                station.setCode(generatedCode);
                log.debug("Generated unique code [{}] for station [{}]", generatedCode, station.getName());
            }
        }
        stationRepository.saveAll(stations);
        log.info("All stations saved successfully");
    }

    private String generateUniqueCode(String name) {
        String prefix = name.replaceAll("[^A-Za-z]", "").toUpperCase();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;

        String random = String.valueOf((int) (Math.random() * 9000) + 1000);

        String code = prefix + random;
        log.trace("Generated code [{}] for station [{}]", code, name);
        return code;
    }

    public List<Station> getStations() {
        log.info("Fetching all stations");
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            log.warn("No stations found in database");
            throw new StationNotFoundException("No stations found in Database");
        }
        log.info("Fetched {} stations", stations.size());
        return stations;
    }

    public Station getStationByCode(String code) {
        log.info("Fetching station with code: {}", code);
        return stationRepository.findByCode(code)
                .orElseThrow(() -> {
                    log.error("Station with code [{}] not found", code);
                    return new StationNotFoundException("Station with code: " + code + " doesn't exist");
                });
    }

    public void updateStation(String code, @NonNull Station updatedStation) {
        log.info("Updating station with code: {}", code);
        Station existingStation = getStationByCode(code);

        existingStation.setName(updatedStation.getName());
        existingStation.setState(updatedStation.getState());
        existingStation.setCity(updatedStation.getCity());

        stationRepository.save(existingStation);
        log.info("Station [{}] updated successfully", code);
    }

    @Transactional
    public void removeStation(String code) {
        log.warn("Attempting to delete station with code: {}", code);
        if (!stationRepository.existsByCode(code)) {
            log.error("Station with code [{}] not found, cannot delete", code);
            throw new StationNotFoundException("Station with code: " + code + " doesn't exist");
        }
        stationRepository.deleteByCode(code);
        log.info("Station [{}] deleted successfully", code);
    }
}
