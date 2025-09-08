package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.TrainNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.repository.TrainRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TrainService {

    private TrainRepository trainRepository;

    /*
     * Manage Trains
        * Add        *  Multiple Trains at once
        * Get Trains        *  by unique code (code must be in CAPS)
        * Update Trains
        * Delete Trains
     */

    public void addTrain(Train train) {
        if (train.getCode() == null || train.getCode().isBlank()) {
            train.setCode(generateUniqueCode(train.getName()));
        }
        trainRepository.save(train);
    }

    public void addTrains(List<Train> trains) {
        for (Train train : trains) {
            if (train.getCode() == null || train.getCode().isBlank()) {
                train.setCode(generateUniqueCode(train.getName()));
            }
        }
        trainRepository.saveAll(trains);
    }

    private String generateUniqueCode(String name) {
        String prefix = name.replaceAll("[^A-Za-z]", "").toUpperCase();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;

        String random = String.valueOf((int)(Math.random() * 9000) + 1000);

        return prefix + random;
    }

    public List<Train> getTrains() {
        List<Train> trains = trainRepository.findAll();
        if (trains.isEmpty()) {
            throw new TrainNotFoundException("No Trains found in Database");
        }
        return trains;
    }

    public Train getTrainByCode(String code) {
        return trainRepository.findByCode(code)
                .orElseThrow(() -> new TrainNotFoundException("Train doesn't exist"));
    }

    public void updateTrain(String code, Train updatedTrain) {
        Train existingTrain = getTrainByCode(code);

        existingTrain.setName(updatedTrain.getName());
        existingTrain.setCapacity(updatedTrain.getCapacity());

        trainRepository.save(existingTrain);
    }

    @Transactional
    public void removeTrain(String code) {
        if (!trainRepository.existsByCode(code)) {
            throw new TrainNotFoundException("Train doesn't exist");
        }
        trainRepository.deleteByCode(code);
    }
}
