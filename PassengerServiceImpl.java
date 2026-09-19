package com.aec.railway.service;

import com.aec.railway.model.Passenger;
import com.aec.railway.repository.PassengerRepository;

public class PassengerServiceImpl
        implements PassengerService {

    private PassengerRepository repository;

    public PassengerServiceImpl(
            PassengerRepository repository) {

        this.repository = repository;
    }

    @Override
    public boolean addPassenger(
            Passenger passenger) {

        if (passenger.getPassengerName()
                == null ||
            passenger.getPassengerName()
                .trim().isEmpty()) {

            System.out.println(
                    "Passenger name cannot be empty."
            );

            return false;
        }

        if (passenger.getAge() <= 0 ||
            passenger.getAge() > 120) {

            System.out.println(
                    "Invalid age."
            );

            return false;
        }

        if (!passenger.getGender()
                .equalsIgnoreCase("Male") &&
            !passenger.getGender()
                .equalsIgnoreCase("Female") &&
            !passenger.getGender()
                .equalsIgnoreCase("Other")) {

            System.out.println(
                    "Gender must be Male, Female or Other."
            );

            return false;
        }

        if (!passenger.getPhone()
                .matches("\\d{10}")) {

            System.out.println(
                    "Phone must contain exactly 10 digits."
            );

            return false;
        }

        if (repository.phoneExists(
                passenger.getPhone())) {

            System.out.println(
                    "Phone number already exists."
            );

            return false;
        }

        return repository.addPassenger(
                passenger
        );
    }

    @Override
    public Passenger findPassengerById(
            int passengerId) {

        return repository.findPassengerById(
                passengerId
        );
    }

    @Override
    public boolean updatePassenger(
            Passenger passenger) {

        Passenger existing =
                repository.findPassengerById(
                        passenger.getPassengerId()
                );

        if (existing == null) {

            System.out.println(
                    "Passenger ID "
                    + passenger.getPassengerId()
                    + " not found!"
            );

            System.out.println(
                    "Cannot update."
            );

            return false;
        }

        if (passenger.getAge() <= 0 ||
            passenger.getAge() > 120) {

            System.out.println(
                    "Invalid age."
            );

            return false;
        }

        if (!passenger.getPhone()
                .matches("\\d{10}")) {

            System.out.println(
                    "Invalid phone number."
            );

            return false;
        }

        if (repository
                .phoneExistsForOtherPassenger(
                        passenger.getPhone(),
                        passenger.getPassengerId()
                )) {

            System.out.println(
                    "Phone number already belongs " +
                    "to another passenger."
            );

            return false;
        }

        return repository.updatePassenger(
                passenger
        );
    }

    @Override
    public boolean deletePassenger(
            int passengerId) {

        Passenger existing =
                repository.findPassengerById(
                        passengerId
                );

        if (existing == null) {

            System.out.println(
                    "Passenger ID "
                    + passengerId
                    + " not found!"
            );

            System.out.println(
                    "Cannot delete."
            );

            return false;
        }

        return repository.deletePassenger(
                passengerId
        );
    }
}