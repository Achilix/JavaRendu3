package com.yourpackage.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Salle {
    private int id;
    private StringProperty name;
    private int capacity;
    private String location;

    public Salle() {
        this.name = new SimpleStringProperty();
    }

    public Salle(int id, String name, int capacity, String location) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.capacity = capacity;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (Capacity: %d, Location: %s)", name.get(), capacity, location);
    }
}