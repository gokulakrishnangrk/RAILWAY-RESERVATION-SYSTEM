package com.aec.railway.service;

import java.util.List;

import com.aec.railway.model.Train;

public interface TrainService {

    boolean addTrain(Train train);

    List<Train> getAllTrains();

    Train findTrainById(int trainId);

    List<Train> searchTrain(
            String source,
            String destination);

    boolean updateTrain(Train train);

    boolean deleteTrain(int trainId);
}