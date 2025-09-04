package com.dasvoximon.railwaysystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleTypeMismatch(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Malformed JSON or incorrect data types. " + ex.getMostSpecificCause().getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGenericError(Exception ex) {
//        return new ResponseEntity<>(Map.of("error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<?> handleDoctorNotFoundException(StationNotFoundException stationNotFoundException) {
        return new ResponseEntity<>(Map.of("error", stationNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TrainNotFoundException.class)
    public ResponseEntity<?> handleTrainNotFoundException(TrainNotFoundException trainNotFoundException) {
        return new ResponseEntity<>(Map.of("error", trainNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StationConflictException.class)
    public ResponseEntity<?> handleStationConflictException(StationConflictException stationConflictException) {
        return new ResponseEntity<>(Map.of("error", stationConflictException.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<?> handleRouteNotFoundException(RouteNotFoundException routeNotFoundException) {
        return new ResponseEntity<>(Map.of("error", routeNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<?> handleScheduleNotFoundException(ScheduleNotFoundException scheduleNotFoundException) {
        return new ResponseEntity<>(Map.of("error", scheduleNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<?> handleReservationNotFoundException(ReservationNotFoundException reservationNotFoundException) {
        return new ResponseEntity<>(Map.of("error", reservationNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<?> handlePassengerNotFoundException(PassengerNotFoundException passengerNotFoundException) {
        return new ResponseEntity<>(Map.of("status", passengerNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatAlreadyTakenException.class)
    public ResponseEntity<?> handleSeatAlreadyTakenException(SeatAlreadyTakenException seatAlreadyTakenException) {
        return new ResponseEntity<>(Map.of("error", seatAlreadyTakenException.getMessage()), HttpStatus.CONFLICT);
    }
}
