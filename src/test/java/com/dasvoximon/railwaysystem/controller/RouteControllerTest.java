package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.RouteDetailsDTO;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.service.RouteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private RouteService routeService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RouteService routeService() {
            return Mockito.mock(RouteService.class);
        }
    }

    @Test
    void addRoute_ShouldReturn201() throws Exception {
        String json = """
            {"trainCode":"T1","originStationCode":"S1","destinationStationCode":"S2","distance":120.0}
        """;

        mockMvc.perform(post("/api/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Route added successfully"));
    }

    @Test
    void getRoutes_ShouldReturnList() throws Exception {
        Mockito.when(routeService.getRoutes()).thenReturn(List.of(new Route()));

        mockMvc.perform(get("/api/routes"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getRouteSummaries_ShouldReturnList() throws Exception {
        Mockito.when(routeService.getRoutesAndStationAndTrain()).thenReturn(List.of(new RouteDetailsDTO()));

        mockMvc.perform(get("/api/routes/summary"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$").isArray());
    }
}
