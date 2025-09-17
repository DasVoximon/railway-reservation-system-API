package com.dasvoximon.railwaysystem.integration;

import com.dasvoximon.railwaysystem.model.dto.request.RouteRequest;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.repository.RouteRepository;
import com.dasvoximon.railwaysystem.repository.StationRepository;
import com.dasvoximon.railwaysystem.repository.TrainRepository;
import com.dasvoximon.railwaysystem.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class RouteIntegrationTest {

    @Autowired private RouteService routeService;
    @Autowired private TrainRepository trainRepository;
    @Autowired private StationRepository stationRepository;
    @Autowired private RouteRepository routeRepository;

    @BeforeEach
    void setup() {
        Train train = new Train();
        train.setCode("T1");
        train.setName("Train A");
        train.setCapacity(1);
        trainRepository.save(train);

        Station origin = new Station();
        origin.setCode("S1");
        stationRepository.save(origin);

        Station destination = new Station();
        destination.setCode("S2");
        stationRepository.save(destination);
    }

    @Test
    void addRoute_ShouldPersistToDatabase() {
        RouteRequest request = new RouteRequest("T1", "S1", "S2", BigDecimal.valueOf(150.0));

        routeService.addRoute(request);

        List<Route> routes = routeRepository.findAll();
        assertThat(routes).hasSize(1);
        assertThat(routes.get(0).getOriginStation().getCode()).isEqualTo("S1");
    }
}
