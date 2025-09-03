package com.dasvoximon.railwaysystem.controllers;

import com.dasvoximon.railwaysystem.dto.ScheduleRequest;
import com.dasvoximon.railwaysystem.entities.Schedule;
import com.dasvoximon.railwaysystem.services.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> addSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.addSchedule(scheduleRequest);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return new ResponseEntity<>(scheduleService.getSchedules(), HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Schedule>> findSchedules(@RequestParam @Valid String originStation,
                                                        @RequestParam @Valid String destinationStation,
                                                        @RequestParam @Valid LocalDate date) {

        return new ResponseEntity<>(scheduleService.findSchedules(originStation, destinationStation, date), HttpStatus.FOUND);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long scheduleId,
                                                 @Valid @RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.updateSchedules(scheduleId, scheduleRequest);
        return new ResponseEntity<>("Schedule updated successfully", HttpStatus.valueOf("UPDATED"));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.removeSchedules(scheduleId);
        return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.valueOf("DELETED"));
    }
}
