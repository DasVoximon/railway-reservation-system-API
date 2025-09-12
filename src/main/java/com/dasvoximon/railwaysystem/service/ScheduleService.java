package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.exception.RouteNotFoundException;
import com.dasvoximon.railwaysystem.exception.ScheduleNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Route;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.repository.RouteRepository;
import com.dasvoximon.railwaysystem.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    public Schedule addSchedule(ScheduleRequest scheduleRequest) {
        log.info("Adding schedule for routeId [{}] on [{}]", scheduleRequest.getRouteId(), scheduleRequest.getTravelDate());

        Long routeId = scheduleRequest.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> {
                    log.error("Route with id [{}] not found while creating schedule", routeId);
                    return new RouteNotFoundException("Route with id: " + routeId + " doesn't exist");
                });

        Schedule schedule = new Schedule();
        schedule.setRoute(route);
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setTravelDate(scheduleRequest.getTravelDate());
        schedule.setBase_fare(scheduleRequest.getBaseFare());
        schedule.setSeats(scheduleRequest.getSeats());

        Schedule saved = scheduleRepository.save(schedule);
        log.info("Schedule [{}] added successfully for route [{}]", saved.getId(), routeId);
        return saved;
    }

    public void addSchedules(List<ScheduleRequest> scheduleRequests) {
        log.info("Adding {} schedules in bulk", scheduleRequests.size());
        for (ScheduleRequest scheduleRequest : scheduleRequests) {
            Long routeId = scheduleRequest.getRouteId();
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> {
                        log.error("Route with id [{}] not found while bulk-adding schedules", routeId);
                        return new RouteNotFoundException("Route with id: " + routeId + " doesn't exist");
                    });

            Schedule schedule = new Schedule();
            schedule.setRoute(route);
            schedule.setArrivalTime(scheduleRequest.getArrivalTime());
            schedule.setDepartureTime(scheduleRequest.getDepartureTime());
            schedule.setTravelDate(scheduleRequest.getTravelDate());
            schedule.setBase_fare(scheduleRequest.getBaseFare());
            schedule.setSeats(scheduleRequest.getSeats());

            scheduleRepository.save(schedule);
            log.debug("Added schedule for route [{}] on date [{}]", routeId, scheduleRequest.getTravelDate());
        }
        log.info("All schedules added successfully");
    }

    public List<Schedule> getSchedules() {
        log.info("Fetching all schedules");
        List<Schedule> schedules = scheduleRepository.findAll();
        if (schedules.isEmpty()) {
            log.warn("No schedules found in database");
            throw new ScheduleNotFoundException("No Schedules found in database");
        }
        log.info("Fetched {} schedules", schedules.size());
        return schedules;
    }

    public List<ScheduleDetailsDTO> getSchedulesSummary() {
        log.info("Fetching schedule summaries");
        List<ScheduleDetailsDTO> schedules = scheduleRepository.findSchedulesSummary();
        if (schedules.isEmpty()) {
            log.warn("No schedule summaries found");
            throw new ScheduleNotFoundException("No Schedules found in database");
        }
        log.info("Fetched {} schedule summaries", schedules.size());
        return schedules;
    }

    public Schedule updateSchedules(Long scheduleId, ScheduleRequest scheduleRequest) {
        log.info("Updating schedule with id [{}]", scheduleId);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    log.error("Schedule with id [{}] not found for update", scheduleId);
                    return new ScheduleNotFoundException("Schedule with id: " + scheduleId + " doesn't exist");
                });

        Long routeId = scheduleRequest.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> {
                    log.error("Route with id [{}] not found while updating schedule [{}]", routeId, scheduleId);
                    return new RouteNotFoundException("Route with id: " + routeId + " doesn't exist");
                });

        schedule.setRoute(route);
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setTravelDate(scheduleRequest.getTravelDate());
        schedule.setBase_fare(scheduleRequest.getBaseFare());
        schedule.setSeats(scheduleRequest.getSeats());

        Schedule updated = scheduleRepository.save(schedule);
        log.info("Schedule [{}] updated successfully", updated.getId());
        return updated;
    }

    public void removeSchedules(Long scheduleId) {
        log.warn("Attempting to delete schedule with id [{}]", scheduleId);
        if (!scheduleRepository.existsById(scheduleId)) {
            log.error("Schedule with id [{}] not found, cannot delete", scheduleId);
            throw new ScheduleNotFoundException("Schedule with id: " + scheduleId + " doesn't exist");
        }
        scheduleRepository.deleteById(scheduleId);
        log.info("Schedule [{}] deleted successfully", scheduleId);
    }

    public List<Schedule> searchSchedules(String origin, String destination, LocalDate date) {
        log.info("Searching schedules with origin [{}], destination [{}], date [{}]", origin, destination, date);

        if (origin == null && destination == null && date == null) {
            log.debug("No search filters provided, returning all schedules");
            return getSchedules();
        }

        List<Schedule> customSchedules = scheduleRepository.search(origin, destination, date);
        if (customSchedules.isEmpty()) {
            log.warn("No schedules found for search: origin [{}], destination [{}], date [{}]", origin, destination, date);
            throw new ScheduleNotFoundException("No schedule found for this search");
        }
        log.info("Found {} schedules for given search criteria", customSchedules.size());
        return customSchedules;
    }
}
