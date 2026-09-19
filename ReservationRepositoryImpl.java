package com.aec.railway.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aec.railway.database.Database;
import com.aec.railway.model.Reservation;

public class ReservationRepositoryImpl
        implements ReservationRepository {

    @Override
    public boolean bookTicket(
            Reservation reservation) {

        Connection con = null;

        try {

            con = Database.getConnection();

            con.setAutoCommit(false);

            String insertSql =
                    "INSERT INTO reservation " +
                    "(pnr, passenger_id, train_id, " +
                    "journey_date, seat_no, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(insertSql)
            ) {

                ps.setString(
                        1,
                        reservation.getPnr()
                );

                ps.setInt(
                        2,
                        reservation.getPassengerId()
                );

                ps.setInt(
                        3,
                        reservation.getTrainId()
                );

                ps.setDate(
                        4,
                        reservation.getJourneyDate()
                );

                ps.setInt(
                        5,
                        reservation.getSeatNo()
                );

                ps.setString(
                        6,
                        "CONFIRMED"
                );

                ps.executeUpdate();
            }

            String updateTrainSql =
                    "UPDATE train " +
                    "SET available_seats = " +
                    "available_seats - 1 " +
                    "WHERE train_id=? " +
                    "AND available_seats > 0";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateTrainSql
                            )
            ) {

                ps.setInt(
                        1,
                        reservation.getTrainId()
                );

                int updated =
                        ps.executeUpdate();

                if (updated == 0) {

                    con.rollback();

                    return false;
                }
            }

            con.commit();

            return true;

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            System.out.println(
                    "Booking Error: "
                    + e.getMessage()
            );

            return false;

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public Reservation findByPnr(
            String pnr) {

        String sql =
                "SELECT r.*, " +
                "p.passenger_name, " +
                "t.train_name " +
                "FROM reservation r " +
                "JOIN passenger p " +
                "ON r.passenger_id=p.passenger_id " +
                "JOIN train t " +
                "ON r.train_id=t.train_id " +
                "WHERE r.pnr=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, pnr);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return createReservation(rs);
            }

        } catch (Exception e) {

            System.out.println(
                    "Search Error: "
                    + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<Reservation>
    getPassengerReservations(
            int passengerId) {

        List<Reservation> list =
                new ArrayList<>();

        String sql =
                "SELECT r.*, " +
                "p.passenger_name, " +
                "t.train_name " +
                "FROM reservation r " +
                "JOIN passenger p " +
                "ON r.passenger_id=p.passenger_id " +
                "JOIN train t " +
                "ON r.train_id=t.train_id " +
                "WHERE r.passenger_id=? " +
                "ORDER BY r.reservation_id DESC";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, passengerId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                list.add(
                        createReservation(rs)
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }

        return list;
    }

    private Reservation createReservation(
            ResultSet rs)
            throws Exception {

        return new Reservation(
                rs.getInt("reservation_id"),
                rs.getString("pnr"),
                rs.getInt("passenger_id"),
                rs.getInt("train_id"),
                rs.getDate("journey_date"),
                rs.getInt("seat_no"),
                rs.getString("status"),
                rs.getString("passenger_name"),
                rs.getString("train_name")
        );
    }

    @Override
    public boolean cancelTicket(
            String pnr) {

        Connection con = null;

        try {

            con = Database.getConnection();

            con.setAutoCommit(false);

            String selectSql =
                    "SELECT train_id, status " +
                    "FROM reservation " +
                    "WHERE pnr=?";

            int trainId;
            String status;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(selectSql)
            ) {

                ps.setString(1, pnr);

                ResultSet rs =
                        ps.executeQuery();

                if (!rs.next()) {

                    return false;
                }

                trainId =
                        rs.getInt("train_id");

                status =
                        rs.getString("status");
            }

            if (!status.equals("CONFIRMED")) {

                return false;
            }

            String cancelSql =
                    "UPDATE reservation " +
                    "SET status='CANCELLED' " +
                    "WHERE pnr=?";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    cancelSql
                            )
            ) {

                ps.setString(1, pnr);

                ps.executeUpdate();
            }

            String trainSql =
                    "UPDATE train " +
                    "SET available_seats = " +
                    "available_seats + 1 " +
                    "WHERE train_id=?";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    trainSql
                            )
            ) {

                ps.setInt(1, trainId);

                ps.executeUpdate();
            }

            con.commit();

            return true;

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            System.out.println(
                    "Cancellation Error: "
                    + e.getMessage()
            );

            return false;

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean seatAlreadyBooked(
            int trainId,
            int seatNo,
            java.sql.Date journeyDate) {

        String sql =
                "SELECT reservation_id " +
                "FROM reservation " +
                "WHERE train_id=? " +
                "AND seat_no=? " +
                "AND journey_date=? " +
                "AND status='CONFIRMED'";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, trainId);
            ps.setInt(2, seatNo);
            ps.setDate(3, journeyDate);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public int getNextSeatNumber(
            int trainId,
            java.sql.Date journeyDate) {

        String sql =
                "SELECT MAX(seat_no) " +
                "FROM reservation " +
                "WHERE train_id=? " +
                "AND journey_date=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, trainId);
            ps.setDate(2, journeyDate);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) + 1;
            }

        } catch (Exception e) {

            System.out.println(
                    "Seat Error: "
                    + e.getMessage()
            );
        }

        return 1;
    }

    @Override
    public void showBookingReport() {

        String sql =
                "SELECT COUNT(*) AS total, " +
                "SUM(status='CONFIRMED') AS confirmed, " +
                "SUM(status='CANCELLED') AS cancelled " +
                "FROM reservation";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                System.out.println();

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "       RAILWAY BOOKING REPORT"
                );

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "Total Bookings : "
                        + rs.getInt("total")
                );

                System.out.println(
                        "Confirmed      : "
                        + rs.getInt("confirmed")
                );

                System.out.println(
                        "Cancelled      : "
                        + rs.getInt("cancelled")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Report Error: "
                    + e.getMessage()
            );
        }
    }
}