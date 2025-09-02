/*
 * Manage Stations
 * Add Station
 * Add Multiple Stations at once
 * Generate Unique Code
 * Get Stations
 * Get Stations by unique code (code must be in CAPS)
 * Update Stations
 * Delete Stations
 */

package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.exceptions.StationNotFoundException;
import com.dasvoximon.railwaysystem.entities.Station;
import com.dasvoximon.railwaysystem.repositories.StationRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StationService {

    private final StationRepository stationRepository;

    public void addStation(Station station) {
        if (station.getCode() == null || station.getCode().isBlank()) {
            station.setCode(generateUniqueCode(station.getName()));
        }
        stationRepository.save(station);
    }

    public void addStations(List<Station> stations) {
        for (Station station : stations) {
            if (station.getCode() == null || station.getCode().isBlank()) {
                station.setCode(generateUniqueCode(station.getName()));
            }
        }
        stationRepository.saveAll(stations);
    }

    private String generateUniqueCode(String name) {
        String prefix = name.replaceAll("[^A-Za-z]", "").toUpperCase();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;

        String random = String.valueOf((int)(Math.random() * 9000) + 1000);

        return prefix + random;
    }

    public List<Station> getStations() {
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            throw new StationNotFoundException("No stations found in Database");
        }
        return stations;
    }

    public Station getStationByCode(String code) {
        return stationRepository.findByCode(code)
                .orElseThrow(() -> new StationNotFoundException("Station with code: " + code + " doesn't exist"));
    }

    public Station getStationByCodeIgnoreCase(String code) {
        return stationRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new StationNotFoundException("Station doesn't exist"));
    }

    public void updateStation(String code, @NonNull Station updatedStation) {
        Station existingStation = getStationByCode(code);

        existingStation.setName(updatedStation.getName());
        existingStation.setState(updatedStation.getState());
        existingStation.setCity(updatedStation.getCity());

        stationRepository.save(existingStation);
    }

    public void removeStation(String code) {
        if (!stationRepository.existsByCode(code)) {
            throw new StationNotFoundException("Station with code: " + code + " doesn't exist");
        }
        stationRepository.deleteByCode(code);
    }
}
