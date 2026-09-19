package com.aec.railway.controller;

import java.util.List;

import com.aec.railway.model.Reservation;
import com.aec.railway.service.ReservationService;

public class ReservationController {

    private ReservationService service;

    public ReservationController(
            ReservationService service) {

        this.service = service;
    }

    public boolean bookTicket(
            Reservation reservation) {

        return service.bookTicket(
                reservation
        );
    }

    public Reservation findByPnr(
            String pnr) {

        return service.findByPnr(pnr);
    }

    public List<Reservation>
    getPassengerReservations(
            int passengerId) {

        return service
                .getPassengerReservations(
                        passengerId
                );
    }

    public boolean cancelTicket(
            String pnr) {

        return service.cancelTicket(pnr);
    }

    public void showBookingReport() {

        service.showBookingReport();
    }
}