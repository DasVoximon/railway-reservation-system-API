package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.entity.Station;
import com.dasvoximon.railwaysystem.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository.deleteAll(); // clean db before each test
    }

    @Test
    void addStation_ShouldReturn201_AndSaveToDb() throws Exception {
        String json = """
                {
                  "code": "ST001",
                  "name": "Central Station",
                  "state": "Lagos",
                  "city": "Ikeja"
                }
                """;

        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Station added successfully"));

        assertThat(stationRepository.findAll()).hasSize(1);
        assertThat(stationRepository.findAll().get(0).getCode()).isEqualTo("ST001");
    }

    @Test
    void getStations_ShouldReturnListFromDb() throws Exception {
        Station station = new Station();
        station.setCode("ST001");
        station.setName("Central Station");
        station.setState("Lagos");
        station.setCity("Ikeja");
        stationRepository.save(station);

        mockMvc.perform(get("/api/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ST001"))
                .andExpect(jsonPath("$[0].name").value("Central Station"));
    }

    @Test
    void getStationByCode_ShouldReturnStation() throws Exception {
        Station station = new Station();
        station.setCode("ST001");
        station.setName("Central Station");
        station.setState("Lagos");
        station.setCity("Ikeja");
        stationRepository.save(station);

        mockMvc.perform(get("/api/stations/ST001"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.code").value("ST001"))
                .andExpect(jsonPath("$.name").value("Central Station"));
    }

    @Test
    void updateStation_ShouldReturn200_AndUpdateDb() throws Exception {
        Station station = new Station();
        station.setCode("ST001");
        station.setName("Central Station");
        station.setState("Lagos");
        station.setCity("Ikeja");
        stationRepository.save(station);

        String json = """
                {
                  "code": "ST001",
                  "name": "Updated Station",
                  "state": "Ogun",
                  "city": "Abeokuta"
                }
                """;

        mockMvc.perform(put("/api/stations/ST001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Station updated successfully"));

        Station updated = stationRepository.findByCode("ST001").orElseThrow();
        assertThat(updated.getName()).isEqualTo("Updated Station");
        assertThat(updated.getState()).isEqualTo("Ogun");
    }

    @Test
    void deleteStation_ShouldReturn200_AndRemoveFromDb() throws Exception {
        Station station = new Station();
        station.setCode("ST001");
        station.setName("Central Station");
        station.setState("Lagos");
        station.setCity("Ikeja");
        stationRepository.save(station);

        mockMvc.perform(delete("/api/stations/ST001"))
                .andExpect(status().isOk())
                .andExpect(content().string("Station deleted successfully"));

        assertThat(stationRepository.existsByCode("ST001")).isFalse();
    }
}
