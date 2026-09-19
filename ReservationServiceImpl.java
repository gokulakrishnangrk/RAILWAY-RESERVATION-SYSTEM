package com.aec.railway.service;

import java.util.List;
import java.util.UUID;

import com.aec.railway.model.Reservation;
import com.aec.railway.model.Train;
import com.aec.railway.repository.ReservationRepository;
import com.aec.railway.repository.TrainRepository;

public class ReservationServiceImpl
        implements ReservationService {

    private ReservationRepository reservationRepository;

    private TrainRepository trainRepository;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            TrainRepository trainRepository) {

        this.reservationRepository =
                reservationRepository;

        this.trainRepository =
                trainRepository;
    }

    @Override
    public boolean bookTicket(
            Reservation reservation) {

        Train train =
                trainRepository.findTrainById(
                        reservation.getTrainId()
                );

        if (train == null) {

            System.out.println(
                    "Train ID "
                    + reservation.getTrainId()
                    + " not found!"
            );

            return false;
        }

        if (train.getAvailableSeats() <= 0) {

            System.out.println(
                    "No seats available."
            );

            return false;
        }

        if (reservation.getJourneyDate()
                == null) {

            System.out.println(
                    "Journey date is required."
            );

            return false;
        }

        int seat =
                reservationRepository
                        .getNextSeatNumber(
                                reservation.getTrainId(),
                                reservation.getJourneyDate()
                        );

        if (seat > train.getTotalSeats()) {

            System.out.println(
                    "No seat available."
            );

            return false;
        }

        reservation.setSeatNo(seat);

        String pnr =
                "PNR"
                + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        reservation.setPnr(pnr);

        reservation.setStatus(
                "CONFIRMED"
        );

        return reservationRepository
                .bookTicket(reservation);
    }

    @Override
    public Reservation findByPnr(
            String pnr) {

        return reservationRepository
                .findByPnr(pnr);
    }

    @Override
    public List<Reservation>
    getPassengerReservations(
            int passengerId) {

        return reservationRepository
                .getPassengerReservations(
                        passengerId
                );
    }

    @Override
    public boolean cancelTicket(
            String pnr) {

        Reservation reservation =
                reservationRepository
                        .findByPnr(pnr);

        if (reservation == null) {

            System.out.println(
                    "PNR "
                    + pnr
                    + " not found!"
            );

            System.out.println(
                    "Cannot cancel."
            );

            return false;
        }

        if (!reservation.getStatus()
                .equals("CONFIRMED")) {

            System.out.println(
                    "Ticket is already cancelled."
            );

            return false;
        }

        return reservationRepository
                .cancelTicket(pnr);
    }

    @Override
    public void showBookingReport() {

        reservationRepository
                .showBookingReport();
    }
}