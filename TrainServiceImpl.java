package com.aec.railway.service;

import java.util.List;

import com.aec.railway.model.Train;
import com.aec.railway.repository.TrainRepository;

public class TrainServiceImpl
        implements TrainService {

    private TrainRepository repository;

    public TrainServiceImpl(
            TrainRepository repository) {

        this.repository = repository;
    }

    @Override
    public boolean addTrain(Train train) {

        if (train.getTrainName() == null ||
            train.getTrainName().trim().isEmpty()) {

            System.out.println(
                    "Train name cannot be empty."
            );

            return false;
        }

        if (train.getSource() == null ||
            train.getSource().trim().isEmpty()) {

            System.out.println(
                    "Source cannot be empty."
            );

            return false;
        }

        if (train.getDestination() == null ||
            train.getDestination().trim().isEmpty()) {

            System.out.println(
                    "Destination cannot be empty."
            );

            return false;
        }

        if (train.getTotalSeats() <= 0) {

            System.out.println(
                    "Seats must be greater than 0."
            );

            return false;
        }

        train.setAvailableSeats(
                train.getTotalSeats()
        );

        return repository.addTrain(train);
    }

    @Override
    public List<Train> getAllTrains() {

        return repository.getAllTrains();
    }

    @Override
    public Train findTrainById(
            int trainId) {

        return repository.findTrainById(
                trainId
        );
    }

    @Override
    public List<Train> searchTrain(
            String source,
            String destination) {

        return repository.searchTrain(
                source,
                destination
        );
    }

    @Override
    public boolean updateTrain(
            Train train) {

        Train existing =
                repository.findTrainById(
                        train.getTrainId()
                );

        if (existing == null) {

            System.out.println(
                    "Train ID "
                    + train.getTrainId()
                    + " not found!"
            );

            System.out.println(
                    "Cannot update."
            );

            return false;
        }

        if (train.getTotalSeats() <= 0) {

            System.out.println(
                    "Seats must be greater than 0."
            );

            return false;
        }

        int bookedSeats =
                existing.getTotalSeats()
                - existing.getAvailableSeats();

        if (train.getTotalSeats()
                < bookedSeats) {

            System.out.println(
                    "Total seats cannot be less " +
                    "than already booked seats."
            );

            return false;
        }

        train.setAvailableSeats(
                train.getTotalSeats()
                - bookedSeats
        );

        return repository.updateTrain(
                train
        );
    }

    @Override
    public boolean deleteTrain(
            int trainId) {

        Train existing =
                repository.findTrainById(
                        trainId
                );

        if (existing == null) {

            System.out.println(
                    "Train ID "
                    + trainId
                    + " not found!"
            );

            System.out.println(
                    "Cannot delete."
            );

            return false;
        }

        return repository.deleteTrain(
                trainId
        );
    }
}