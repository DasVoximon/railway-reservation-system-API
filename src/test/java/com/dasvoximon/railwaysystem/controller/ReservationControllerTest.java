package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationService reservationService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ReservationService reservationService() {
            return Mockito.mock(ReservationService.class);
        }
    }

    @Test
    void bookTicket_ShouldReturn201() throws Exception {
        Mockito.doNothing().when(reservationService).makeReservations(any(ReservationRequest.class));

        String json = """
                {
                  "scheduleId": 1,
                  "email": "john@example.com",
                  "seatNumber": 5
                }
                """;

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ticket booked"));
    }

    @Test
    void viewReservations_ShouldReturnList() throws Exception {
        Mockito.when(reservationService.viewReservations()).thenReturn(List.of(new Reservation()));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$").isArray());
    }
}
