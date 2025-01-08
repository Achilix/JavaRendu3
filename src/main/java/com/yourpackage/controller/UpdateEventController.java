package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class UpdateEventController {

    private final EventDAO eventDao = new EventDAO();
    private Event event;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dateField;

    public void setEvent(Event event) {
        this.event = event;
        nameField.setText(event.getName());
        descriptionField.setText(event.getDescription());
        dateField.setText(event.getDate().toString());
    }

    @FXML
    private void handleUpdate() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            LocalDate localDate = LocalDate.parse(dateField.getText());
            Date date = Date.valueOf(localDate);

            event.setName(name);
            event.setDescription(description);
            event.setDate(date);

            eventDao.update(event);

            showAlert("Update Event", "Event updated successfully!");

            // Close the current stage
            Stage currentStage = (Stage) nameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            showAlert("Error", "Invalid input: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}