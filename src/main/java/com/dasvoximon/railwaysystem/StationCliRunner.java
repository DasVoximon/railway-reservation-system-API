package com.dasvoximon.railwaysystem;

import com.dasvoximon.railwaysystem.services.TrainService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StationCliRunner implements CommandLineRunner {

    private final TrainService trainService;

    @Override
    public void run(String... args) throws Exception {

//        System.out.println("Hello World!!!");
//        System.out.println(trainService.getTrains());
    }

//    private List<Schedule> viewSchedule() {
//        return trainService.getSchedules();
//    }
}
