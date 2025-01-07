package com.yourpackage.Model;

import java.util.Date;
import java.sql.Time;

public class Reservation {
    private int id;
    private int userId;
    private int eventId;
    private int salleId;
    private int terrainId;
    private Date reservationDate;
    private Time startTime;
    private Time endTime;

    public Reservation(int id, int userId, int eventId, int salleId, int terrainId, Date reservationDate, Time startTime, Time endTime) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.salleId = salleId;
        this.terrainId = terrainId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public int getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}