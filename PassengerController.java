package com.aec.railway.controller;

import com.aec.railway.model.Passenger;
import com.aec.railway.service.PassengerService;

public class PassengerController {

    private PassengerService service;

    public PassengerController(
            PassengerService service) {

        this.service = service;
    }

    public boolean addPassenger(
            Passenger passenger) {

        return service.addPassenger(
                passenger
        );
    }

    public Passenger findPassengerById(
            int passengerId) {

        return service.findPassengerById(
                passengerId
        );
    }

    public boolean updatePassenger(
            Passenger passenger) {

        return service.updatePassenger(
                passenger
        );
    }

    public boolean deletePassenger(
            int passengerId) {

        return service.deletePassenger(
                passengerId
        );
    }
}