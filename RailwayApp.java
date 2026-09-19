package com.aec.railway.main;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.aec.railway.controller.PassengerController;
import com.aec.railway.controller.ReservationController;
import com.aec.railway.controller.TrainController;

import com.aec.railway.model.Passenger;
import com.aec.railway.model.Reservation;
import com.aec.railway.model.Train;

import com.aec.railway.repository.PassengerRepositoryImpl;
import com.aec.railway.repository.ReservationRepositoryImpl;
import com.aec.railway.repository.TrainRepositoryImpl;

import com.aec.railway.service.PassengerServiceImpl;
import com.aec.railway.service.ReservationServiceImpl;
import com.aec.railway.service.TrainServiceImpl;

public class RailwayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REPOSITORIES

        TrainRepositoryImpl trainRepository =
                new TrainRepositoryImpl();

        PassengerRepositoryImpl passengerRepository =
                new PassengerRepositoryImpl();

        ReservationRepositoryImpl reservationRepository =
                new ReservationRepositoryImpl();

        // SERVICES

        TrainServiceImpl trainService =
                new TrainServiceImpl(
                        trainRepository
                );

        PassengerServiceImpl passengerService =
                new PassengerServiceImpl(
                        passengerRepository
                );

        ReservationServiceImpl reservationService =
                new ReservationServiceImpl(
                        reservationRepository,
                        trainRepository
                );

        // CONTROLLERS

        TrainController trainController =
                new TrainController(
                        trainService
                );

        PassengerController passengerController =
                new PassengerController(
                        passengerService
                );

        ReservationController reservationController =
                new ReservationController(
                        reservationService
                );

        while (true) {

            showMenu();

            int choice =
                    readInt(
                            sc,
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:

                    addTrain(
                            sc,
                            trainController
                    );

                    break;

                case 2:

                    viewTrains(
                            trainController
                    );

                    break;

                case 3:

                    searchTrain(
                            sc,
                            trainController
                    );

                    break;

                case 4:

                    updateTrain(
                            sc,
                            trainController
                    );

                    break;

                case 5:

                    deleteTrain(
                            sc,
                            trainController
                    );

                    break;

                case 6:

                    addPassenger(
                            sc,
                            passengerController
                    );

                    break;

                case 7:

                    updatePassenger(
                            sc,
                            passengerController
                    );

                    break;

                case 8:

                    deletePassenger(
                            sc,
                            passengerController
                    );

                    break;

                case 9:

                    bookTicket(
                            sc,
                            passengerController,
                            trainController,
                            reservationController
                    );

                    break;

                case 10:

                    searchPNR(
                            sc,
                            reservationController
                    );

                    break;

                case 11:

                    cancelTicket(
                            sc,
                            reservationController
                    );

                    break;

                case 12:

                    passengerHistory(
                            sc,
                            passengerController,
                            reservationController
                    );

                    break;

                case 13:

                    reservationController
                            .showBookingReport();

                    break;

                case 14:

                    System.out.println(
                            "Thank you for using " +
                            "Railway Reservation System!"
                    );

                    sc.close();

                    return;

                default:

                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }
    }

    // ==========================================
    // MENU
    // ==========================================

    private static void showMenu() {

        System.out.println();

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "       RAILWAY RESERVATION SYSTEM"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "1.  Add Train"
        );

        System.out.println(
                "2.  View All Trains"
        );

        System.out.println(
                "3.  Search Train"
        );

        System.out.println(
                "4.  Update Train"
        );

        System.out.println(
                "5.  Delete Train"
        );

        System.out.println(
                "6.  Add Passenger"
        );

        System.out.println(
                "7.  Update Passenger"
        );

        System.out.println(
                "8.  Delete Passenger"
        );

        System.out.println(
                "9.  Book Ticket"
        );

        System.out.println(
                "10. Search Ticket by PNR"
        );

        System.out.println(
                "11. Cancel Ticket"
        );

        System.out.println(
                "12. Passenger Booking History"
        );

        System.out.println(
                "13. Booking Report"
        );

        System.out.println(
                "14. Exit"
        );

        System.out.println(
                "=============================================="
        );
    }

    // ==========================================
    // ADD TRAIN
    // ==========================================

    private static void addTrain(
            Scanner sc,
            TrainController controller) {

        System.out.println();

        System.out.println(
                "----------- ADD TRAIN -----------"
        );

        String name =
                readString(
                        sc,
                        "Train Name: "
                );

        String source =
                readString(
                        sc,
                        "Source: "
                );

        String destination =
                readString(
                        sc,
                        "Destination: "
                );

        int seats =
                readInt(
                        sc,
                        "Total Seats: "
                );

        Train train =
                new Train(
                        0,
                        name,
                        source,
                        destination,
                        seats,
                        seats
                );

        if (controller.addTrain(train)) {

            System.out.println(
                    "Train added successfully!"
            );

        } else {

            System.out.println(
                    "Train could not be added."
            );
        }
    }

    // ==========================================
    // VIEW TRAINS
    // ==========================================

    private static void viewTrains(
            TrainController controller) {

        List<Train> trains =
                controller.getAllTrains();

        System.out.println();

        System.out.println(
                "---------------- TRAIN LIST ----------------"
        );

        System.out.printf(
                "%-8s %-20s %-15s %-15s %-12s %-15s%n",
                "ID",
                "Train",
                "Source",
                "Destination",
                "Total",
                "Available"
        );

        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        for (Train train : trains) {

            System.out.println(train);
        }
    }

    // ==========================================
    // SEARCH TRAIN
    // ==========================================

    private static void searchTrain(
            Scanner sc,
            TrainController controller) {

        String source =
                readString(
                        sc,
                        "Source: "
                );

        String destination =
                readString(
                        sc,
                        "Destination: "
                );

        List<Train> trains =
                controller.searchTrain(
                        source,
                        destination
                );

        if (trains.isEmpty()) {

            System.out.println(
                    "No trains found."
            );

            return;
        }

        System.out.printf(
                "%-8s %-20s %-15s %-15s %-12s %-15s%n",
                "ID",
                "Train",
                "Source",
                "Destination",
                "Total",
                "Available"
        );

        for (Train train : trains) {

            System.out.println(train);
        }
    }

    // ==========================================
    // UPDATE TRAIN
    // ==========================================

    private static void updateTrain(
            Scanner sc,
            TrainController controller) {

        int id =
                readInt(
                        sc,
                        "Enter Train ID: "
                );

        Train existing =
                controller.findTrainById(id);

        if (existing == null) {

            System.out.println(
                    "Train ID " + id +
                    " not found!"
            );

            System.out.println(
                    "Cannot update."
            );

            return;
        }

        String name =
                readString(
                        sc,
                        "New Train Name: "
                );

        String source =
                readString(
                        sc,
                        "New Source: "
                );

        String destination =
                readString(
                        sc,
                        "New Destination: "
                );

        int seats =
                readInt(
                        sc,
                        "New Total Seats: "
                );

        Train train =
                new Train(
                        id,
                        name,
                        source,
                        destination,
                        seats,
                        0
                );

        if (controller.updateTrain(train)) {

            System.out.println(
                    "Train updated successfully!"
            );

        } else {

            System.out.println(
                    "Train could not be updated."
            );
        }
    }

    // ==========================================
    // DELETE TRAIN
    // ==========================================

    private static void deleteTrain(
            Scanner sc,
            TrainController controller) {

        int id =
                readInt(
                        sc,
                        "Enter Train ID: "
                );

        Train existing =
                controller.findTrainById(id);

        if (existing == null) {

            System.out.println(
                    "Train ID " + id +
                    " not found!"
            );

            System.out.println(
                    "Cannot delete."
            );

            return;
        }

        String confirm =
                readString(
                        sc,
                        "Are you sure? (yes/no): "
                );

        if (!confirm.equalsIgnoreCase(
                "yes")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        if (controller.deleteTrain(id)) {

            System.out.println(
                    "Train deleted successfully!"
            );

        } else {

            System.out.println(
                    "Cannot delete train."
            );

            System.out.println(
                    "Existing reservations may use this train."
            );
        }
    }

    // ==========================================
    // ADD PASSENGER
    // ==========================================

    private static void addPassenger(
            Scanner sc,
            PassengerController controller) {

        System.out.println();

        System.out.println(
                "----------- ADD PASSENGER -----------"
        );

        String name =
                readString(
                        sc,
                        "Passenger Name: "
                );

        int age =
                readInt(
                        sc,
                        "Age: "
                );

        String gender =
                readString(
                        sc,
                        "Gender: "
                );

        String phone =
                readString(
                        sc,
                        "Phone: "
                );

        Passenger passenger =
                new Passenger(
                        0,
                        name,
                        age,
                        gender,
                        phone
                );

        if (controller.addPassenger(
                passenger)) {

            System.out.println(
                    "Passenger added successfully!"
            );

        } else {

            System.out.println(
                    "Passenger could not be added."
            );
        }
    }

    // ==========================================
    // UPDATE PASSENGER
    // ==========================================

    private static void updatePassenger(
            Scanner sc,
            PassengerController controller) {

        int id =
                readInt(
                        sc,
                        "Enter Passenger ID: "
                );

        Passenger existing =
                controller.findPassengerById(id);

        if (existing == null) {

            System.out.println(
                    "Passenger ID " + id +
                    " not found!"
            );

            System.out.println(
                    "Cannot update."
            );

            return;
        }

        String name =
                readString(
                        sc,
                        "New Name: "
                );

        int age =
                readInt(
                        sc,
                        "New Age: "
                );

        String gender =
                readString(
                        sc,
                        "New Gender: "
                );

        String phone =
                readString(
                        sc,
                        "New Phone: "
                );

        Passenger passenger =
                new Passenger(
                        id,
                        name,
                        age,
                        gender,
                        phone
                );

        if (controller.updatePassenger(
                passenger)) {

            System.out.println(
                    "Passenger updated successfully!"
            );

        } else {

            System.out.println(
                    "Passenger could not be updated."
            );
        }
    }

    // ==========================================
    // DELETE PASSENGER
    // ==========================================

    private static void deletePassenger(
            Scanner sc,
            PassengerController controller) {

        int id =
                readInt(
                        sc,
                        "Enter Passenger ID: "
                );

        Passenger existing =
                controller.findPassengerById(id);

        if (existing == null) {

            System.out.println(
                    "Passenger ID " + id +
                    " not found!"
            );

            System.out.println(
                    "Cannot delete."
            );

            return;
        }

        String confirm =
                readString(
                        sc,
                        "Are you sure? (yes/no): "
                );

        if (!confirm.equalsIgnoreCase(
                "yes")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        if (controller.deletePassenger(id)) {

            System.out.println(
                    "Passenger deleted successfully!"
            );

        } else {

            System.out.println(
                    "Passenger could not be deleted."
            );
        }
    }

    // ==========================================
    // BOOK TICKET
    // ==========================================

    private static void bookTicket(
            Scanner sc,
            PassengerController passengerController,
            TrainController trainController,
            ReservationController reservationController) {

        System.out.println();

        System.out.println(
                "----------- BOOK TICKET -----------"
        );

        int passengerId =
                readInt(
                        sc,
                        "Passenger ID: "
                );

        Passenger passenger =
                passengerController
                        .findPassengerById(
                                passengerId
                        );

        if (passenger == null) {

            System.out.println(
                    "Passenger ID "
                    + passengerId
                    + " not found!"
            );

            System.out.println(
                    "Cannot book ticket."
            );

            return;
        }

        int trainId =
                readInt(
                        sc,
                        "Train ID: "
                );

        Train train =
                trainController.findTrainById(
                        trainId
                );

        if (train == null) {

            System.out.println(
                    "Train ID "
                    + trainId
                    + " not found!"
            );

            System.out.println(
                    "Cannot book ticket."
            );

            return;
        }

        System.out.println(
                "Passenger: "
                + passenger.getPassengerName()
        );

        System.out.println(
                "Train: "
                + train.getTrainName()
        );

        System.out.println(
                "Available Seats: "
                + train.getAvailableSeats()
        );

        String date =
                readString(
                        sc,
                        "Journey Date (yyyy-mm-dd): "
                );

        try {

            Date journeyDate =
                    Date.valueOf(date);

            if (journeyDate.toLocalDate()
                    .isBefore(
                            LocalDate.now()
                    )) {

                System.out.println(
                        "Journey date cannot be in the past."
                );

                return;
            }

            Reservation reservation =
                    new Reservation(
                            0,
                            null,
                            passengerId,
                            trainId,
                            journeyDate,
                            0,
                            "CONFIRMED"
                    );

            if (reservationController
                    .bookTicket(
                            reservation
                    )) {

                System.out.println();

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "        TICKET BOOKED SUCCESSFULLY"
                );

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "PNR          : "
                        + reservation.getPnr()
                );

                System.out.println(
                        "Passenger    : "
                        + passenger.getPassengerName()
                );

                System.out.println(
                        "Train        : "
                        + train.getTrainName()
                );

                System.out.println(
                        "Journey Date : "
                        + journeyDate
                );

                System.out.println(
                        "Seat Number  : "
                        + reservation.getSeatNo()
                );

                System.out.println(
                        "Status       : CONFIRMED"
                );

            } else {

                System.out.println(
                        "Ticket booking failed."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid date format."
            );

            System.out.println(
                    "Use yyyy-mm-dd."
            );
        }
    }

    // ==========================================
    // SEARCH PNR
    // ==========================================

    private static void searchPNR(
            Scanner sc,
            ReservationController controller) {

        String pnr =
                readString(
                        sc,
                        "Enter PNR: "
                );

        Reservation reservation =
                controller.findByPnr(pnr);

        if (reservation == null) {

            System.out.println(
                    "PNR " + pnr +
                    " not found!"
            );

            return;
        }

        System.out.println();

        System.out.println(
                "======================================"
        );

        System.out.println(
                "             TICKET DETAILS"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "PNR          : "
                + reservation.getPnr()
        );

        System.out.println(
                "Passenger    : "
                + reservation.getPassengerName()
        );

        System.out.println(
                "Train        : "
                + reservation.getTrainName()
        );

        System.out.println(
                "Journey Date : "
                + reservation.getJourneyDate()
        );

        System.out.println(
                "Seat Number  : "
                + reservation.getSeatNo()
        );

        System.out.println(
                "Status       : "
                + reservation.getStatus()
        );
    }

    // ==========================================
    // CANCEL TICKET
    // ==========================================

    private static void cancelTicket(
            Scanner sc,
            ReservationController controller) {

        String pnr =
                readString(
                        sc,
                        "Enter PNR: "
                );

        Reservation reservation =
                controller.findByPnr(pnr);

        if (reservation == null) {

            System.out.println(
                    "PNR " + pnr +
                    " not found!"
            );

            System.out.println(
                    "Cannot cancel."
            );

            return;
        }

        System.out.println(
                "Passenger: "
                + reservation.getPassengerName()
        );

        System.out.println(
                "Train: "
                + reservation.getTrainName()
        );

        String confirm =
                readString(
                        sc,
                        "Cancel ticket? (yes/no): "
                );

        if (!confirm.equalsIgnoreCase(
                "yes")) {

            System.out.println(
                    "Cancellation cancelled."
            );

            return;
        }

        if (controller.cancelTicket(pnr)) {

            System.out.println(
                    "Ticket cancelled successfully!"
            );

        } else {

            System.out.println(
                    "Ticket could not be cancelled."
            );
        }
    }

    // ==========================================
    // PASSENGER HISTORY
    // ==========================================

    private static void passengerHistory(
            Scanner sc,
            PassengerController passengerController,
            ReservationController reservationController) {

        int passengerId =
                readInt(
                        sc,
                        "Passenger ID: "
                );

        Passenger passenger =
                passengerController
                        .findPassengerById(
                                passengerId
                        );

        if (passenger == null) {

            System.out.println(
                    "Passenger ID "
                    + passengerId
                    + " not found!"
            );

            return;
        }

        List<Reservation> list =
                reservationController
                        .getPassengerReservations(
                                passengerId
                        );

        if (list.isEmpty()) {

            System.out.println(
                    "No booking history."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "----------- BOOKING HISTORY -----------"
        );

        System.out.printf(
                "%-15s %-20s %-15s %-15s %-10s%n",
                "PNR",
                "Train",
                "Date",
                "Seat",
                "Status"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Reservation r : list) {

            System.out.printf(
                    "%-15s %-20s %-15s %-15d %-10s%n",
                    r.getPnr(),
                    r.getTrainName(),
                    r.getJourneyDate(),
                    r.getSeatNo(),
                    r.getStatus()
            );
        }
    }

    // ==========================================
    // INPUT METHODS
    // ==========================================

    private static int readInt(
            Scanner sc,
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static String readString(
            Scanner sc,
            String message) {

        System.out.print(message);

        return sc.nextLine().trim();
    }
}