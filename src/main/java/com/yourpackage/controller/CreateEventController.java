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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CreateEventController {

    private final EventDAO eventDao = new EventDAO();
    private final SalleDAO salleDao = new SalleDAO();
    private final TerrainDAO terrainDao = new TerrainDAO();
    private final ReservationDAO reservationDao = new ReservationDAO();
    private int userId; // Store the user ID

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dateField;

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    private void handleCreate() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            LocalDate localDate = LocalDate.parse(dateField.getText());
            Date date = Date.valueOf(localDate);

            Event event = new Event(0, name, description, date, userId);
            eventDao.add(event);

            if (reserveSalleCheckBox.isSelected()) {
                Salle salle = salleComboBox.getValue();
                if (salle != null) {
                    if (reservationDao.isSalleReserved(salle.getId(), date)) {
                        showAlert("Error", "Salle is already reserved for the selected date.");
                        return;
                    }
                    Reservation salleReservation = new Reservation(0, userId, event.getId(), salle.getId(), 0, date);
                    reservationDao.add(salleReservation);
                } else {
                    showAlert("Error", "Please select a Salle.");
                }
            }

            if (reserveTerrainCheckBox.isSelected()) {
                Terrain terrain = terrainComboBox.getValue();
                if (terrain != null) {
                    if (reservationDao.isTerrainReserved(terrain.getId(), date)) {
                        showAlert("Error", "Terrain is already reserved for the selected date.");
                        return;
                    }
                    Reservation terrainReservation = new Reservation(0, userId, event.getId(), 0, terrain.getId(), date);
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
    private void showUserManagement() {
        openNewWindow("/UserManagement.fxml", "User Management");
    }

    @FXML
    public void initialize() {
        loadSalles();
        loadTerrains();
    }

    public void loadSalles() {
        List<Salle> salles = salleDao.getAll();
        salleComboBox.getItems().setAll(salles);
        System.out.println("Loaded Salles: " + salles); // Debugging statement
    }

    public void loadTerrains() {
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

            // Refresh data after the window is closed
            loadSalles();
            loadTerrains();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open the new window.");
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