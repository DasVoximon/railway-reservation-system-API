/*
 * Manage Schedules
     * Create Schedules
     * Retrieve Schedules
     * Update Schedules
     * Delete Schedules
 */
package com.dasvoximon.railwaysystem.services;

import com.dasvoximon.railwaysystem.dto.ScheduleRequest;
import com.dasvoximon.railwaysystem.exceptions.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exceptions.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.entities.Route;
import com.dasvoximon.railwaysystem.entities.Schedule;
import com.dasvoximon.railwaysystem.repositories.RouteRepository;
import com.dasvoximon.railwaysystem.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    public void addSchedule(ScheduleRequest scheduleRequest) {
        Long routeId = scheduleRequest.getRoute_id();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route with id: " +  routeId + " doesn't exist"));

        Schedule schedule = new Schedule();
        schedule.setRoute(route);
        schedule.setArrival_time(scheduleRequest.getArrival_time());
        schedule.setDeparture_time(scheduleRequest.getDeparture_time());
        schedule.setOperating_days(scheduleRequest.getOperating_days());
        schedule.setBase_fare(scheduleRequest.getBase_fare());
        schedule.setTotal_seats(scheduleRequest.getTotal_seats());
        schedule.setAvailable_seats(scheduleRequest.getAvailable_seats());

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        if (schedules.isEmpty())
            throw new ScheduleNotFoundException("No Schedules found in database");
        return schedules;
    }

    public void updateSchedules(Long schedule_id, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(schedule_id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with id: " + schedule_id + " doesn't exist"));

        Long routeId = scheduleRequest.getRoute_id();
        Route route = routeRepository.findById(routeId)
                        .orElseThrow(() -> new RouteNotFoundException("Route with id: " + routeId + " doesn't exist"));

        schedule.setRoute(route);
        schedule.setArrival_time(scheduleRequest.getArrival_time());
        schedule.setDeparture_time(scheduleRequest.getDeparture_time());
        schedule.setOperating_days(scheduleRequest.getOperating_days());
        schedule.setBase_fare(scheduleRequest.getBase_fare());
        schedule.setTotal_seats(scheduleRequest.getTotal_seats());
        schedule.setAvailable_seats(scheduleRequest.getAvailable_seats());

        scheduleRepository.save(schedule);
    }

    public void removeSchedules(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleNotFoundException("Schedule with id: " + scheduleId + " doesn't exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    // search schedules by origin, destination, date, time

}
