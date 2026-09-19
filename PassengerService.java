package com.aec.railway.service;

import com.aec.railway.model.Passenger;

public interface PassengerService {

    boolean addPassenger(
            Passenger passenger);

    Passenger findPassengerById(
            int passengerId);

    boolean updatePassenger(
            Passenger passenger);

    boolean deletePassenger(
            int passengerId);
}