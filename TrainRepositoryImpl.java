package com.aec.railway.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aec.railway.database.Database;
import com.aec.railway.model.Train;

public class TrainRepositoryImpl
        implements TrainRepository {

    @Override
    public boolean addTrain(Train train) {

        String sql =
                "INSERT INTO train " +
                "(train_name, source, destination, " +
                "total_seats, available_seats) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    train.getTrainName()
            );

            ps.setString(
                    2,
                    train.getSource()
            );

            ps.setString(
                    3,
                    train.getDestination()
            );

            ps.setInt(
                    4,
                    train.getTotalSeats()
            );

            ps.setInt(
                    5,
                    train.getAvailableSeats()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Database Error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Train> getAllTrains() {

        List<Train> trains =
                new ArrayList<>();

        String sql =
                "SELECT * FROM train " +
                "ORDER BY train_id";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                trains.add(
                        new Train(
                                rs.getInt("train_id"),
                                rs.getString("train_name"),
                                rs.getString("source"),
                                rs.getString("destination"),
                                rs.getInt("total_seats"),
                                rs.getInt("available_seats")
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error: "
                    + e.getMessage()
            );
        }

        return trains;
    }

    @Override
    public Train findTrainById(
            int trainId) {

        String sql =
                "SELECT * FROM train " +
                "WHERE train_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, trainId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new Train(
                        rs.getInt("train_id"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Database Error: "
                    + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<Train> searchTrain(
            String source,
            String destination) {

        List<Train> trains =
                new ArrayList<>();

        String sql =
                "SELECT * FROM train " +
                "WHERE source LIKE ? " +
                "AND destination LIKE ?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    "%" + source + "%"
            );

            ps.setString(
                    2,
                    "%" + destination + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                trains.add(
                        new Train(
                                rs.getInt("train_id"),
                                rs.getString("train_name"),
                                rs.getString("source"),
                                rs.getString("destination"),
                                rs.getInt("total_seats"),
                                rs.getInt("available_seats")
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Search Error: "
                    + e.getMessage()
            );
        }

        return trains;
    }

    @Override
    public boolean updateTrain(
            Train train) {

        String sql =
                "UPDATE train SET " +
                "train_name=?, source=?, " +
                "destination=?, total_seats=? " +
                "WHERE train_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    train.getTrainName()
            );

            ps.setString(
                    2,
                    train.getSource()
            );

            ps.setString(
                    3,
                    train.getDestination()
            );

            ps.setInt(
                    4,
                    train.getTotalSeats()
            );

            ps.setInt(
                    5,
                    train.getTrainId()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update Error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean deleteTrain(
            int trainId) {

        String sql =
                "DELETE FROM train " +
                "WHERE train_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, trainId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Cannot delete train."
            );

            return false;
        }
    }
}