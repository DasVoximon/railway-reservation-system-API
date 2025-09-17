package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.StationNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private Station station;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        station = new Station();
        station.setCode("ST001");
        station.setName("Central Station");
        station.setState("Lagos");
        station.setCity("Ikeja");
    }

    @Test
    void addStation_ShouldSaveStation() {
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        stationService.addStation(station);

        verify(stationRepository, times(1)).save(station);
    }

    @Test
    void getStations_ShouldReturnList_WhenNotEmpty() {
        when(stationRepository.findAll()).thenReturn(List.of(station));

        List<Station> stations = stationService.getStations();

        assertThat(stations).hasSize(1);
        assertThat(stations.get(0).getName()).isEqualTo("Central Station");
    }

    @Test
    void getStations_ShouldThrow_WhenEmpty() {
        when(stationRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> stationService.getStations())
                .isInstanceOf(StationNotFoundException.class)
                .hasMessageContaining("No stations found");
    }

    @Test
    void getStationByCode_ShouldReturnStation_WhenExists() {
        when(stationRepository.findByCode("ST001")).thenReturn(Optional.of(station));

        Station found = stationService.getStationByCode("ST001");

        assertThat(found).isNotNull();
        assertThat(found.getCode()).isEqualTo("ST001");
    }

    @Test
    void getStationByCode_ShouldThrow_WhenNotFound() {
        when(stationRepository.findByCode("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stationService.getStationByCode("UNKNOWN"))
                .isInstanceOf(StationNotFoundException.class);
    }

    @Test
    void updateStation_ShouldUpdateAndSave() {
        when(stationRepository.findByCode("ST001")).thenReturn(Optional.of(station));

        Station updated = new Station();
        updated.setName("Updated Name");
        updated.setState("Ogun");
        updated.setCity("Abeokuta");

        stationService.updateStation("ST001", updated);

        verify(stationRepository, times(1)).save(any(Station.class));
        assertThat(station.getName()).isEqualTo("Updated Name");
    }

    @Test
    void removeStation_ShouldDelete_WhenExists() {
        when(stationRepository.existsByCode("ST001")).thenReturn(true);

        stationService.removeStation("ST001");

        verify(stationRepository, times(1)).deleteByCode("ST001");
    }

    @Test
    void removeStation_ShouldThrow_WhenNotExists() {
        when(stationRepository.existsByCode("ST001")).thenReturn(false);

        assertThatThrownBy(() -> stationService.removeStation("ST001"))
                .isInstanceOf(StationNotFoundException.class);
    }
}
