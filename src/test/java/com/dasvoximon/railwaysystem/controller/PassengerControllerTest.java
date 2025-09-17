package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.request.PassengerRegistrationRequest;
import com.dasvoximon.railwaysystem.model.entity.Passenger;
import com.dasvoximon.railwaysystem.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PassengerController.class)
@Import(PassengerControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PassengerService passengerService() {
            return Mockito.mock(PassengerService.class);
        }
    }

    @Test
    void addPassenger_ShouldReturnCreated() throws Exception {
        PassengerRegistrationRequest request =
                new PassengerRegistrationRequest("John", "M", "Doe", "john@example.com", "09025428255");

        doNothing().when(passengerService).addPassenger(any(PassengerRegistrationRequest.class));

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Passenger added"));

        verify(passengerService, times(1)).addPassenger(any(PassengerRegistrationRequest.class));
    }

    @Test
    void getPassengers_ShouldReturnPassengers() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setEmail("john@example.com");

        when(passengerService.viewPassenger()).thenReturn(List.of(passenger));

        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isFound()) // HTTP 302
                .andExpect(jsonPath("$[0].email").value("john@example.com"));

        verify(passengerService, times(1)).viewPassenger();
    }

    @Test
    void deletePassenger_ShouldReturnOk() throws Exception {
        doNothing().when(passengerService).deletePassenger(1L);

        mockMvc.perform(delete("/api/passengers/{passengerId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Passenger deleted"));

        verify(passengerService, times(1)).deletePassenger(1L);
    }
}
