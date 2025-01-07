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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Date;
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
    private ComboBox<Salle> salleComboBox;

    @FXML
    private ComboBox<Terrain> terrainComboBox;

    @FXML
    public void initialize() {
        loadSalles();
        loadTerrains();
    }

    private void loadSalles() {
        List<Salle> salles = salleDao.getAll();
        salleComboBox.getItems().setAll(salles);
    }

    private void loadTerrains() {
        List<Terrain> terrains = terrainDao.getAll();
        terrainComboBox.getItems().setAll(terrains);
    }

    @FXML
    private void handleCreate() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        Date date = Date.valueOf(dateField.getText());

        Event event = new Event(0, name, description, date);
        eventDao.add(event);

        Salle selectedSalle = salleComboBox.getSelectionModel().getSelectedItem();
        Terrain selectedTerrain = terrainComboBox.getSelectionModel().getSelectedItem();

        if (selectedSalle != null) {
            Reservation salleReservation = new Reservation(0, event.getId(), selectedSalle.getId(), date);
            reservationDao.add(salleReservation);
        }

        if (selectedTerrain != null) {
            Reservation terrainReservation = new Reservation(0, event.getId(), selectedTerrain.getId(), date);
            reservationDao.add(terrainReservation);
        }

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