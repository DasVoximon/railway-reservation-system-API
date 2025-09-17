package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exception.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.model.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.repository.RouteRepository;
import com.dasvoximon.railwaysystem.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock private ScheduleRepository scheduleRepository;
    @Mock private RouteRepository routeRepository;
    @InjectMocks private ScheduleService scheduleService;

    @Test
    void addSchedule_ShouldSave_WhenRouteExists() {
        // given
        ScheduleRequest req = new ScheduleRequest(1L,
                LocalTime.now(), LocalTime.NOON, LocalDate.now(),
                BigDecimal.valueOf(200), 50);

        Route route = new Route();
        route.setId(1L);

        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));
        when(scheduleRepository.save(any(Schedule.class)))
                .thenAnswer(inv -> {
                    Schedule s = inv.getArgument(0);
                    s.setId(10L);
                    return s;
                });

        // when
        Schedule saved = scheduleService.addSchedule(req);

        // then
        assertThat(saved.getId()).isEqualTo(10L);
        assertThat(saved.getRoute()).isEqualTo(route);
        verify(scheduleRepository).save(any(Schedule.class));
    }

    @Test
    void addSchedule_ShouldThrow_WhenRouteMissing() {
        ScheduleRequest req = new ScheduleRequest(999L,
                LocalTime.now(), LocalTime.NOON, LocalDate.now(),
                BigDecimal.valueOf(200), 50);

        when(routeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.addSchedule(req))
                .isInstanceOf(RouteNotFoundException.class);
    }

    @Test
    void getSchedules_ShouldThrow_WhenEmpty() {
        when(scheduleRepository.findAll()).thenReturn(List.of());
        assertThatThrownBy(() -> scheduleService.getSchedules())
                .isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    void removeSchedules_ShouldDelete_WhenExists() {
        when(scheduleRepository.existsById(1L)).thenReturn(true);

        scheduleService.removeSchedules(1L);

        verify(scheduleRepository).deleteById(1L);
    }

    @Test
    void removeSchedules_ShouldThrow_WhenNotExists() {
        when(scheduleRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> scheduleService.removeSchedules(1L))
                .isInstanceOf(ScheduleNotFoundException.class);
    }
}
