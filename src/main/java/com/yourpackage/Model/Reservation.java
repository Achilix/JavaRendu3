package com.yourpackage.Model;

import java.util.Date;

public class Reservation {
    private int id;
    private int userId; 
    private int resourceId; 
    private Date reservationDate;

    
    public Reservation() {}

    public Reservation(int id, int userId, int resourceId, Date reservationDate) {
        this.id = id;
        this.userId = userId;
        this.resourceId = resourceId;
        this.reservationDate = reservationDate;
    }

    
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

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", userId=" + userId + ", resourceId=" + resourceId + ", reservationDate=" + reservationDate + "]";
    }
}
