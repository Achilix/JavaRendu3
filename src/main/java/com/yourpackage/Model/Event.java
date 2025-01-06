package com.yourpackage.Model;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private String description;
    private Date date;

    public Event() {}

    public Event(int id, String name, String description, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", description=" + description + ", date=" + date + "]";
    }
}
