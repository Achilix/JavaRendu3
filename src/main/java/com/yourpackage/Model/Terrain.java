package com.yourpackage.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Terrain {
    private int id;
    private StringProperty name;
    private String type;

    public Terrain() {
        this.name = new SimpleStringProperty();
    }

    public Terrain(int id, String name, String type) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (Type: %s)", name.get(), type);
    }
}