package com.aec.railway.service;

import java.util.List;

import com.aec.railway.model.Reservation;

public interface ReservationService {

    boolean bookTicket(
            Reservation reservation);

    Reservation findByPnr(
            String pnr);

    List<Reservation>
    getPassengerReservations(
            int passengerId);

    boolean cancelTicket(
            String pnr);

    void showBookingReport();
}