package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.Model.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    private final EventDAO eventDao = new EventDAO();

    @FXML
    private ListView<Event> eventsListView;

    @FXML
    public void initialize() {
        loadEvents();
    }

    private void loadEvents() {
        List<Event> events = eventDao.getAll();
        eventsListView.getItems().setAll(events);
    }

    @FXML
    private void handleCreateEvent() {
        loadFXML("/CreateEvent.fxml");
    }

    @FXML
    private void handleUpdateEvent() {
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
                Parent root = loader.load();

                // Pass the selected event to the UpdateEventController
                UpdateEventController controller = loader.getController();
                controller.setEvent(selectedEvent);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                // Refresh the events list after updating
                loadEvents();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select an event to update.");
        }
    }

    @FXML
    private void handleDeleteEvent() {
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventDao.delete(selectedEvent.getId());
            eventsListView.getItems().remove(selectedEvent);
        } else {
            showAlert("No Selection", "Please select an event to delete.");
        }
    }

    @FXML
    private void handleReserveSalle() {
        // Implement the logic for reserving a salle
        showAlert("Reserve Salle", "Reserve Salle functionality is not implemented yet.");
    }

    @FXML
    private void handleReserveTerrain() {
        // Implement the logic for reserving a terrain
        showAlert("Reserve Terrain", "Reserve Terrain functionality is not implemented yet.");
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
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