package com.dasvoximon.railwaysystem.integration;

import com.dasvoximon.railwaysystem.model.dto.request.ReservationRequest;
import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.dasvoximon.railwaysystem.repository.ReservationRepository;
import com.dasvoximon.railwaysystem.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationIntegrationTest {

    @Autowired private ReservationService reservationService;
    @Autowired private ReservationRepository reservationRepository;

    @Test
    void testReservationPersistence() {
        // prepare dummy data (would need Passenger + Schedule saved first)
        Reservation reservation = new Reservation();
        reservation.setSeatNumber(5);

        reservationRepository.save(reservation);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(1);
    }
}
