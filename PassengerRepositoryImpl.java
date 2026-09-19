package com.aec.railway.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aec.railway.database.Database;
import com.aec.railway.model.Passenger;

public class PassengerRepositoryImpl
        implements PassengerRepository {

    @Override
    public boolean addPassenger(
            Passenger passenger) {

        String sql =
                "INSERT INTO passenger " +
                "(passenger_name, age, gender, phone) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    passenger.getPassengerName()
            );

            ps.setInt(
                    2,
                    passenger.getAge()
            );

            ps.setString(
                    3,
                    passenger.getGender()
            );

            ps.setString(
                    4,
                    passenger.getPhone()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Passenger Error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public Passenger findPassengerById(
            int passengerId) {

        String sql =
                "SELECT * FROM passenger " +
                "WHERE passenger_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, passengerId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public boolean updatePassenger(
            Passenger passenger) {

        String sql =
                "UPDATE passenger SET " +
                "passenger_name=?, age=?, " +
                "gender=?, phone=? " +
                "WHERE passenger_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    passenger.getPassengerName()
            );

            ps.setInt(
                    2,
                    passenger.getAge()
            );

            ps.setString(
                    3,
                    passenger.getGender()
            );

            ps.setString(
                    4,
                    passenger.getPhone()
            );

            ps.setInt(
                    5,
                    passenger.getPassengerId()
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
    public boolean deletePassenger(
            int passengerId) {

        String sql =
                "DELETE FROM passenger " +
                "WHERE passenger_id=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, passengerId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete Error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean phoneExists(
            String phone) {

        String sql =
                "SELECT passenger_id " +
                "FROM passenger " +
                "WHERE phone=?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, phone);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public boolean phoneExistsForOtherPassenger(
            String phone,
            int passengerId) {

        String sql =
                "SELECT passenger_id " +
                "FROM passenger " +
                "WHERE phone=? " +
                "AND passenger_id<>?";

        try (
                Connection con =
                        Database.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, phone);
            ps.setInt(2, passengerId);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            return false;
        }
    }
}