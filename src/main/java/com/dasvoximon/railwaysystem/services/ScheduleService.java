/*
 * Manage Schedules
     * Create Schedules
     * Retrieve Schedules
     * Update Schedules
     * Delete Schedules
 */
package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.dto.details.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.exceptions.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.entities.Route;
import com.dasvoximon.railwaysystem.entities.Schedule;
import com.dasvoximon.railwaysystem.repositories.RouteRepository;
import com.dasvoximon.railwaysystem.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    public Schedule addSchedule(ScheduleRequest scheduleRequest) {
        Long routeId = scheduleRequest.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route with id: " +  routeId + " doesn't exist"));

        Schedule schedule = new Schedule();
        schedule.setRoute(route);
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setTravelDate(scheduleRequest.getTravelDate());
        schedule.setBase_fare(scheduleRequest.getBaseFare());
        schedule.setSeats(scheduleRequest.getSeats());

        return scheduleRepository.save(schedule);
    }

    public void addSchedules(List<ScheduleRequest> scheduleRequests) {
        for (ScheduleRequest scheduleRequest :  scheduleRequests) {
            Long routeId = scheduleRequest.getRouteId();
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new RouteNotFoundException("Route with id: " +  routeId + " doesn't exist"));

            Schedule schedule = new Schedule();
            schedule.setRoute(route);
            schedule.setArrivalTime(scheduleRequest.getArrivalTime());
            schedule.setDepartureTime(scheduleRequest.getDepartureTime());
            schedule.setTravelDate(scheduleRequest.getTravelDate());
            schedule.setBase_fare(scheduleRequest.getBaseFare());
            schedule.setSeats(scheduleRequest.getSeats());

            scheduleRepository.save(schedule);
        }
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        if (schedules.isEmpty())
            throw new ScheduleNotFoundException("No Schedules found in database");
        return schedules;
    }

    public List<ScheduleDetailsDTO> getSchedulesSummary() {
        List<ScheduleDetailsDTO> schedules = scheduleRepository.findSchedulesSummary();
        if (schedules.isEmpty())
            throw new ScheduleNotFoundException("No Schedules found in database");
        return schedules;
    }

    public Schedule updateSchedules(Long schedule_id, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(schedule_id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with id: " + schedule_id + " doesn't exist"));

        Long routeId = scheduleRequest.getRouteId();
        Route route = routeRepository.findById(routeId)
                        .orElseThrow(() -> new RouteNotFoundException("Route with id: " + routeId + " doesn't exist"));

        schedule.setRoute(route);
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setTravelDate(scheduleRequest.getTravelDate());
        schedule.setBase_fare(scheduleRequest.getBaseFare());
        schedule.setSeats(scheduleRequest.getSeats());

        return scheduleRepository.save(schedule);
    }

    public void removeSchedules(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleNotFoundException("Schedule with id: " + scheduleId + " doesn't exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    public List<Schedule> searchSchedules(String origin, String destination, LocalDate date) {
        if (origin == null && destination == null && date == null) return getSchedules();

        List<Schedule> customSchedules = scheduleRepository.search(origin, destination, date);
        if (customSchedules.isEmpty()) throw new ScheduleNotFoundException("No schedule found for this search");
        return customSchedules;
    }

}
