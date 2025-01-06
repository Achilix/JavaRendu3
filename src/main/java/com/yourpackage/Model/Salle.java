package com.yourpackage.Model;
public class Salle {
    private int id;
    private String name;
    private int capacity;
    private String location;

    
    public Salle() {}

    public Salle(int id, String name, int capacity, String location) {
        this.id = id;
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Salle [id=" + id + ", name=" + name + ", capacity=" + capacity + ", location=" + location + "]";
    }
}
