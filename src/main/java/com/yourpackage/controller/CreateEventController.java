package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage; // Add this import statement

import java.sql.Date;

public class CreateEventController {

    private final EventDAO eventDao = new EventDAO();

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dateField;

    @FXML
    private void handleCreate() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        Date date = Date.valueOf(dateField.getText());

        Event event = new Event(0, name, description, date);
        eventDao.add(event);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Create Event");
        alert.setHeaderText(null);
        alert.setContentText("Event created successfully!");
        alert.showAndWait();

        // Close the current stage
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();
    }
}