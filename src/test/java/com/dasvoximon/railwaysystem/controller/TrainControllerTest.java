package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.model.wrapper.Trains;
import com.dasvoximon.railwaysystem.service.TrainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class TrainControllerTest {

    private TrainService trainService;
    private TrainController trainController;

    @BeforeEach
    void setUp() {
        trainService = Mockito.mock(TrainService.class);
        trainController = new TrainController(trainService);
    }

    @Test
    void addTrain_ShouldReturnCreatedMessage() {
        Train train = new Train();
        train.setCode("T001");
        train.setName("Express");

        Mockito.doNothing().when(trainService).addTrain(any(Train.class));

        var response = trainController.addTrain(train);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo("Train added successfully");
        Mockito.verify(trainService).addTrain(train);
    }

    @Test
    void getTrains_ShouldReturnTrainsList() {
        Train train = new Train();
        train.setCode("T001");
        train.setName("Express");

        Mockito.when(trainService.getTrains()).thenReturn(List.of(train));

        var response = trainController.getTrains();

        assertThat(response.getStatusCode().value()).isEqualTo(302);
        assertThat(response.getBody()).isInstanceOf(Trains.class);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getTrains()).hasSize(1);
    }

    @Test
    void updateTrain_ShouldReturnOkMessage() {
        Train updated = new Train();
        updated.setName("Updated Express");

        Mockito.doNothing().when(trainService).updateTrain("T001", updated);

        var response = trainController.updateTrain("T001", updated);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Train updated successfully");
        Mockito.verify(trainService).updateTrain("T001", updated);
    }

    @Test
    void removeTrain_ShouldReturnOkMessage() {
        Mockito.doNothing().when(trainService).removeTrain("T001");

        var response = trainController.removeTrain("T001");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Train deleted successfully");
        Mockito.verify(trainService).removeTrain("T001");
    }
}
