package com.aec.railway.controller;

import java.util.List;

import com.aec.railway.model.Train;
import com.aec.railway.service.TrainService;

public class TrainController {

    private TrainService service;

    public TrainController(
            TrainService service) {

        this.service = service;
    }

    public boolean addTrain(
            Train train) {

        return service.addTrain(train);
    }

    public List<Train> getAllTrains() {

        return service.getAllTrains();
    }

    public Train findTrainById(
            int trainId) {

        return service.findTrainById(
                trainId
        );
    }

    public List<Train> searchTrain(
            String source,
            String destination) {

        return service.searchTrain(
                source,
                destination
        );
    }

    public boolean updateTrain(
            Train train) {

        return service.updateTrain(
                train
        );
    }

    public boolean deleteTrain(
            int trainId) {

        return service.deleteTrain(
                trainId
        );
    }
}