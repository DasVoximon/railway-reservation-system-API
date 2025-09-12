package com.dasvoximon.railwaysystem.service;

import com.dasvoximon.railwaysystem.exception.TrainNotFoundException;
import com.dasvoximon.railwaysystem.model.entity.Train;
import com.dasvoximon.railwaysystem.repository.TrainRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public void addTrain(Train train) {
        log.info("Adding new train: {}", train.getName());
        if (train.getCode() == null || train.getCode().isBlank()) {
            String generatedCode = generateUniqueCode(train.getName());
            train.setCode(generatedCode);
            log.debug("Generated unique train code: {}", generatedCode);
        }
        trainRepository.save(train);
        log.info("Train [{}] saved successfully", train.getCode());
    }

    public void addTrains(List<Train> trains) {
        log.info("Adding {} trains", trains.size());
        for (Train train : trains) {
            if (train.getCode() == null || train.getCode().isBlank()) {
                String generatedCode = generateUniqueCode(train.getName());
                train.setCode(generatedCode);
                log.debug("Generated unique train code for {}: {}", train.getName(), generatedCode);
            }
        }
        trainRepository.saveAll(trains);
        log.info("All trains saved successfully");
    }

    private String generateUniqueCode(String name) {
        String prefix = name.replaceAll("[^A-Za-z]", "").toUpperCase();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;

        String random = String.valueOf((int) (Math.random() * 9000) + 1000);

        String code = prefix + random;
        log.trace("Generated code {} for train {}", code, name);
        return code;
    }

    public List<Train> getTrains() {
        log.info("Fetching all trains from database");
        List<Train> trains = trainRepository.findAll();
        if (trains.isEmpty()) {
            log.warn("No trains found in the database");
            throw new TrainNotFoundException("No Trains found in Database");
        }
        log.info("Fetched {} trains", trains.size());
        return trains;
    }

    public Train getTrainByCode(String code) {
        log.info("Fetching train with code: {}", code);
        return trainRepository.findByCode(code)
                .orElseThrow(() -> {
                    log.error("Train with code {} not found", code);
                    return new TrainNotFoundException("Train doesn't exist");
                });
    }

    public void updateTrain(String code, Train updatedTrain) {
        log.info("Updating train with code: {}", code);
        Train existingTrain = getTrainByCode(code);

        existingTrain.setName(updatedTrain.getName());
        existingTrain.setCapacity(updatedTrain.getCapacity());

        trainRepository.save(existingTrain);
        log.info("Train [{}] updated successfully", code);
    }

    @Transactional
    public void removeTrain(String code) {
        log.warn("Attempting to delete train with code: {}", code);
        if (!trainRepository.existsByCode(code)) {
            log.error("Train with code {} not found, cannot delete", code);
            throw new TrainNotFoundException("Train doesn't exist");
        }
        trainRepository.deleteByCode(code);
        log.info("Train [{}] deleted successfully", code);
    }
}
