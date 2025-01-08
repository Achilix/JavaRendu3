package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.DAO.ReservationDAO;
import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.DAO.TerrainDAO;
import com.yourpackage.Model.Event;
import com.yourpackage.Model.Reservation;
import com.yourpackage.Model.Salle;
import com.yourpackage.Model.Terrain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateEventController {

    private final EventDAO eventDao = new EventDAO();
    private final SalleDAO salleDao = new SalleDAO();
    private final TerrainDAO terrainDao = new TerrainDAO();
    private final ReservationDAO reservationDao = new ReservationDAO();

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private CheckBox reserveSalleCheckBox;

    @FXML
    private ComboBox<Salle> salleComboBox;

    @FXML
    private CheckBox reserveTerrainCheckBox;

    @FXML
    private ComboBox<Terrain> terrainComboBox;

    @FXML
    private VBox contentArea; // Ensure this is defined

    @FXML
    private void handleCreate() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            LocalDate localDate = LocalDate.parse(dateField.getText());
            Date date = Date.valueOf(localDate);
            LocalTime localStartTime = LocalTime.parse(startTimeField.getText());
            Time startTime = Time.valueOf(localStartTime);
            LocalTime localEndTime = LocalTime.parse(endTimeField.getText());
            Time endTime = Time.valueOf(localEndTime);

            // Assuming you have a user ID associated with the event
            int userId = 1; // Replace with the actual user ID

            Event event = new Event(0, name, description, date, userId);
            eventDao.add(event);

            if (reserveSalleCheckBox.isSelected()) {
                Salle salle = salleComboBox.getValue();
                if (salle != null) {
                    Reservation salleReservation = new Reservation(0, userId, event.getId(), salle.getId(), 0, date, startTime, endTime);
                    reservationDao.add(salleReservation);
                } else {
                    showAlert("Error", "Please select a Salle.");
                }
            }

            if (reserveTerrainCheckBox.isSelected()) {
                Terrain terrain = terrainComboBox.getValue();
                if (terrain != null) {
                    Reservation terrainReservation = new Reservation(0, userId, event.getId(), 0, terrain.getId(), date, startTime, endTime);
                    reservationDao.add(terrainReservation);
                } else {
                    showAlert("Error", "Please select a Terrain.");
                }
            }

            showAlert("Create Event", "Event created successfully!");

            // Close the current stage
            Stage currentStage = (Stage) nameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            showAlert("Error", "Invalid input: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void showAddSalle() {
        openNewWindow("/AddSalle.fxml", "Add Salle");
    }

    @FXML
    private void showAddTerrain() {
        openNewWindow("/AddTerrain.fxml", "Add Terrain");
    }

    @FXML
    private void showDashboard() {
        openNewWindow("/Dashboard.fxml", "Dashboard");
    }

    @FXML
    public void initialize() {
        loadSalles();
        loadTerrains();
    }

    private void loadSalles() {
        List<Salle> salles = salleDao.getAll();
        salleComboBox.getItems().setAll(salles);
        System.out.println("Loaded Salles: " + salles); // Debugging statement
    }

    private void loadTerrains() {
        List<Terrain> terrains = terrainDao.getAll();
        terrainComboBox.getItems().setAll(terrains);
        System.out.println("Loaded Terrains: " + terrains); // Debugging statement
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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