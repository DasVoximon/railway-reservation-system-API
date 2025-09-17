package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.TrainNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainServiceTest {

    private TrainRepository trainRepository;
    private TrainService trainService;

    @BeforeEach
    void setUp() {
        trainRepository = mock(TrainRepository.class);
        trainService = new TrainService(trainRepository);
    }

    @Test
    void addTrain_ShouldGenerateCodeIfMissing() {
        Train train = new Train();
        train.setName("Express Train");
        train.setCapacity(200);

        trainService.addTrain(train);

        ArgumentCaptor<Train> captor = ArgumentCaptor.forClass(Train.class);
        verify(trainRepository).save(captor.capture());

        Train savedTrain = captor.getValue();
        assertThat(savedTrain.getCode()).isNotBlank();
        assertThat(savedTrain.getName()).isEqualTo("Express Train");
    }

    @Test
    void getTrains_ShouldThrow_WhenEmpty() {
        when(trainRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> trainService.getTrains())
                .isInstanceOf(TrainNotFoundException.class)
                .hasMessage("No Trains found in Database");
    }

    @Test
    void getTrainByCode_ShouldReturnTrain_WhenFound() {
        Train train = new Train(1L, "TR001", "Express", 300);
        when(trainRepository.findByCode("TR001")).thenReturn(Optional.of(train));

        Train result = trainService.getTrainByCode("TR001");

        assertThat(result.getName()).isEqualTo("Express");
    }

    @Test
    void getTrainByCode_ShouldThrow_WhenNotFound() {
        when(trainRepository.findByCode("TR999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trainService.getTrainByCode("TR999"))
                .isInstanceOf(TrainNotFoundException.class)
                .hasMessage("Train doesn't exist");
    }

    @Test
    void updateTrain_ShouldUpdateFields() {
        Train existing = new Train(1L,"TR001", "Old Train", 100);
        Train updated = new Train(1L, "TR001", "New Train", 200);

        when(trainRepository.findByCode("TR001")).thenReturn(Optional.of(existing));

        trainService.updateTrain("TR001", updated);

        ArgumentCaptor<Train> captor = ArgumentCaptor.forClass(Train.class);
        verify(trainRepository).save(captor.capture());

        Train saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("New Train");
        assertThat(saved.getCapacity()).isEqualTo(200);
    }

    @Test
    void removeTrain_ShouldDelete_WhenExists() {
        when(trainRepository.existsByCode("TR001")).thenReturn(true);

        trainService.removeTrain("TR001");

        verify(trainRepository).deleteByCode("TR001");
    }

    @Test
    void removeTrain_ShouldThrow_WhenNotFound() {
        when(trainRepository.existsByCode("TR404")).thenReturn(false);

        assertThatThrownBy(() -> trainService.removeTrain("TR404"))
                .isInstanceOf(TrainNotFoundException.class)
                .hasMessage("Train doesn't exist");
    }
}
