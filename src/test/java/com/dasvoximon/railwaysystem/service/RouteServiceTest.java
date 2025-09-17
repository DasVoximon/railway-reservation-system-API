package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.*;
import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.model.entity.*;
import com.dasvoximon.railwaysystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    @Mock private RouteRepository routeRepository;
    @Mock private TrainRepository trainRepository;
    @Mock private StationRepository stationRepository;

    @InjectMocks private RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRoute_ShouldSave_WhenValidRequest() {
        Train train = new Train(); train.setCode("T1");
        Station origin = new Station(); origin.setCode("S1");
        Station destination = new Station(); destination.setCode("S2");

        RouteRequest request = new RouteRequest("T1", "S1", "S2", BigDecimal.valueOf(120.9));

        when(trainRepository.findByCode("T1")).thenReturn(Optional.of(train));
        when(stationRepository.findByCode("S1")).thenReturn(Optional.of(origin));
        when(stationRepository.findByCode("S2")).thenReturn(Optional.of(destination));
        when(routeRepository.existsByOriginStationAndDestinationStation(origin, destination)).thenReturn(false);

        routeService.addRoute(request);

        verify(routeRepository).save(any(Route.class));
    }

    @Test
    void addRoute_ShouldThrow_WhenSameStations() {
        RouteRequest request = new RouteRequest("T1", "S1", "S1", BigDecimal.valueOf(410.00));
        Train train = new Train(); train.setCode("T1");
        Station station = new Station(); station.setCode("S1");

        when(trainRepository.findByCode("T1")).thenReturn(Optional.of(train));
        when(stationRepository.findByCode("S1")).thenReturn(Optional.of(station));

        assertThatThrownBy(() -> routeService.addRoute(request))
                .isInstanceOf(StationConflictException.class)
                .hasMessage("Origin and destination cannot be same.");
    }

    @Test
    void getRoutes_ShouldThrow_WhenEmpty() {
        when(routeRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> routeService.getRoutes())
                .isInstanceOf(RouteNotFoundException.class)
                .hasMessage("No routes found in database");
    }

    @Test
    void getRoutesAndStationAndTrain_ShouldReturnList() {
        RouteDetailsDTO dto = new RouteDetailsDTO();
        when(routeRepository.findRoute()).thenReturn(List.of(dto));

        List<RouteDetailsDTO> result = routeService.getRoutesAndStationAndTrain();

        assertThat(result).hasSize(1);
    }
}
