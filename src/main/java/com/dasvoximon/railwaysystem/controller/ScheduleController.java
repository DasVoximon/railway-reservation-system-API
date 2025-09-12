package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/schedules")
@Tag(
        name = "Schedule API",
        description = "Endpoints for managing train schedules: create, update, fetch, search, and delete schedules"
)
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(
            summary = "Add Schedule",
            description = "Add a new schedule to the system. Requires train, route, departure and arrival details."
    )
    public ResponseEntity<Schedule> addSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        log.info("[ScheduleController] Creating schedule: routeId={}, travelDate={}, departureTime={}",
                scheduleRequest.getRouteId(), scheduleRequest.getTravelDate(), scheduleRequest.getDepartureTime());

        Schedule created = scheduleService.addSchedule(scheduleRequest);
        log.debug("[ScheduleController] Schedule created successfully with ID={}", created.getId());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(
            summary = "Add Multiple Schedules",
            description = "Add multiple schedules at once by providing a list of schedule requests."
    )
    public ResponseEntity<String> addSchedules(@Valid @RequestBody List<ScheduleRequest> scheduleRequests) {
        log.info("[ScheduleController] Adding {} schedules", scheduleRequests.size());
        scheduleService.addSchedules(scheduleRequests);
        log.debug("[ScheduleController] {} schedules added successfully", scheduleRequests.size());
        return new ResponseEntity<>("Schedules added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Schedules",
            description = "Retrieve all schedules currently available in the system."
    )
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        log.info("[ScheduleController] Fetching all schedules");
        List<Schedule> schedules = scheduleService.getSchedules();
        log.debug("[ScheduleController] Retrieved {} schedules", schedules.size());
        return new ResponseEntity<>(schedules, HttpStatus.FOUND);
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Get Schedules Summary",
            description = "Retrieve a summary view of schedules with key details, mapped to `ScheduleDetailsDTO`."
    )
    public ResponseEntity<List<ScheduleDetailsDTO>> getSchedulesSummary() {
        log.info("[ScheduleController] Fetching schedules summary");
        List<ScheduleDetailsDTO> summary = scheduleService.getSchedulesSummary();
        log.debug("[ScheduleController] Retrieved {} schedule summaries", summary.size());
        return new ResponseEntity<>(summary, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search Schedules",
            description = "Search for schedules by origin station, destination station, and/or date. " +
                    "Any combination of these parameters can be provided."
    )
    public ResponseEntity<List<Schedule>> findSchedules(
            @RequestParam(required = false) String originStation,
            @RequestParam(required = false) String destinationStation,
            @RequestParam(required = false) LocalDate date) {

        log.info("[ScheduleController] Searching schedules: origin={}, destination={}, date={}",
                originStation, destinationStation, date);

        List<Schedule> results = scheduleService.searchSchedules(originStation, destinationStation, date);
        log.debug("[ScheduleController] Found {} matching schedules", results.size());

        return new ResponseEntity<>(results, HttpStatus.FOUND);
    }

    @PutMapping("/{scheduleId}")
    @Operation(
            summary = "Update Schedule",
            description = "Update an existing schedule by its ID. Provide updated details in the request body."
    )
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long scheduleId,
                                                   @Valid @RequestBody ScheduleRequest scheduleRequest) {
        log.info("[ScheduleController] Updating schedule with ID={}", scheduleId);
        Schedule updated = scheduleService.updateSchedules(scheduleId, scheduleRequest);
        log.debug("[ScheduleController] Schedule {} updated successfully", scheduleId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(
            summary = "Delete Schedule",
            description = "Delete a schedule permanently from the system using its ID."
    )
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
        log.warn("[ScheduleController] Deleting schedule with ID={}", scheduleId);
        scheduleService.removeSchedules(scheduleId);
        log.debug("[ScheduleController] Schedule {} deleted successfully", scheduleId);
        return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);
    }
}
