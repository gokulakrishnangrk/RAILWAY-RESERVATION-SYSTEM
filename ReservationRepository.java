package com.aec.railway.repository;

import java.util.List;

import com.aec.railway.model.Reservation;

public interface ReservationRepository {

    boolean bookTicket(
            Reservation reservation);

    Reservation findByPnr(
            String pnr);

    List<Reservation> getPassengerReservations(
            int passengerId);

    boolean cancelTicket(
            String pnr);

    boolean seatAlreadyBooked(
            int trainId,
            int seatNo,
            java.sql.Date journeyDate);

    int getNextSeatNumber(
            int trainId,
            java.sql.Date journeyDate);

    void showBookingReport();
}