package com.aec.railway.model;

import java.sql.Date;

public class Reservation {

    private int reservationId;
    private String pnr;
    private int passengerId;
    private int trainId;
    private Date journeyDate;
    private int seatNo;
    private String status;

    private String passengerName;
    private String trainName;

    public Reservation() {
    }

    public Reservation(
            int reservationId,
            String pnr,
            int passengerId,
            int trainId,
            Date journeyDate,
            int seatNo,
            String status) {

        this.reservationId = reservationId;
        this.pnr = pnr;
        this.passengerId = passengerId;
        this.trainId = trainId;
        this.journeyDate = journeyDate;
        this.seatNo = seatNo;
        this.status = status;
    }

    public Reservation(
            int reservationId,
            String pnr,
            int passengerId,
            int trainId,
            Date journeyDate,
            int seatNo,
            String status,
            String passengerName,
            String trainName) {

        this(
                reservationId,
                pnr,
                passengerId,
                trainId,
                journeyDate,
                seatNo,
                status
        );

        this.passengerName = passengerName;
        this.trainName = trainName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public Date getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(Date journeyDate) {
        this.journeyDate = journeyDate;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
}