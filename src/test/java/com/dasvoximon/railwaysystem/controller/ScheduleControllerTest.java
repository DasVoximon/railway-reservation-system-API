package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
    }

    @Test
    void addSchedule_ShouldReturn201() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setId(1L);

        Mockito.when(scheduleService.addSchedule(Mockito.any(ScheduleRequest.class)))
                .thenReturn(schedule);

        String json = """
            {
              "routeId": 1,
              "travelDate": "2025-09-15",
              "departureTime": "10:00:00",
              "arrivalTime": "12:00:00",
              "baseFare": 100.50,
              "seats": 100
            }
            """;

        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllSchedules_ShouldReturnList() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setId(1L);

        Mockito.when(scheduleService.getSchedules()).thenReturn(List.of(schedule));

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getSchedulesSummary_ShouldReturnList() throws Exception {
        Mockito.when(scheduleService.getSchedulesSummary()).thenReturn(List.of(new ScheduleDetailsDTO()));

        mockMvc.perform(get("/api/schedules/summary"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deleteSchedule_ShouldReturn200() throws Exception {
        Mockito.doNothing().when(scheduleService).removeSchedules(1L);

        mockMvc.perform(delete("/api/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule deleted successfully"));
    }
}
