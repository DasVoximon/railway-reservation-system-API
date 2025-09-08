package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.dto.detail.ScheduleDetailsDTO;
import com.dasvoximon.railwaysystem.model.dto.request.ScheduleRequest;
import com.dasvoximon.railwaysystem.model.entity.Schedule;
import com.dasvoximon.railwaysystem.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> addSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        return new ResponseEntity<>(scheduleService.addSchedule(scheduleRequest), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addSchedules(@Valid @RequestBody List<ScheduleRequest> scheduleRequests) {
        scheduleService.addSchedules(scheduleRequests);
        return new ResponseEntity<>("Schedules added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return new ResponseEntity<>(scheduleService.getSchedules(), HttpStatus.FOUND);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<ScheduleDetailsDTO>> getSchedulesSummary() {
        return new  ResponseEntity<>(scheduleService.getSchedulesSummary(), HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Schedule>> findSchedules(@RequestParam(required = false) String originStation,
                                                        @RequestParam(required = false) String destinationStation,
                                                        @RequestParam(required = false) LocalDate date) {

        return new ResponseEntity<>(scheduleService.searchSchedules(originStation, destinationStation, date), HttpStatus.FOUND);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long scheduleId,
                                                 @Valid @RequestBody ScheduleRequest scheduleRequest) {
        return new ResponseEntity<>(scheduleService.updateSchedules(scheduleId, scheduleRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.removeSchedules(scheduleId);
        return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);
    }
}
