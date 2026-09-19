package com.aec.railway.repository;

import com.aec.railway.model.Passenger;

public interface PassengerRepository {

    boolean addPassenger(
            Passenger passenger);

    Passenger findPassengerById(
            int passengerId);

    boolean updatePassenger(
            Passenger passenger);

    boolean deletePassenger(
            int passengerId);

    boolean phoneExists(String phone);

    boolean phoneExistsForOtherPassenger(
            String phone,
            int passengerId);
}