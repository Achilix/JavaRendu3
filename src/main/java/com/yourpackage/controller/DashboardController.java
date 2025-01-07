package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.DAO.TerrainDAO;
import com.yourpackage.Model.Event;
import com.yourpackage.Model.Salle;
import com.yourpackage.Model.Terrain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    private final EventDAO eventDao = new EventDAO();
    private final SalleDAO salleDao = new SalleDAO();
    private final TerrainDAO terrainDao = new TerrainDAO();

    @FXML
    private ListView<Event> eventsListView;

    @FXML
    private ListView<Salle> sallesListView;

    @FXML
    private ListView<Terrain> terrainsListView;

    @FXML
    public void initialize() {
        loadEvents();
        loadSalles();
        loadTerrains();
    }

    private void loadEvents() {
        List<Event> events = eventDao.getAll();
        eventsListView.getItems().setAll(events);
    }

    private void loadSalles() {
        List<Salle> salles = salleDao.getAll();
        sallesListView.getItems().setAll(salles);
    }

    private void loadTerrains() {
        List<Terrain> terrains = terrainDao.getAll();
        terrainsListView.getItems().setAll(terrains);
    }

    @FXML
    private void handleCreateEvent() {
        loadFXML("/CreateEvent.fxml");
    }

    @FXML
    private void handleReserveSalle() {
        loadFXML("/AddSalle.fxml");
    }

    @FXML
    private void handleReserveTerrain() {
        loadFXML("/AddTerrain.fxml");
    }

    @FXML
    private void handleUpdateEvent() {
        // Implement the logic to update an event
        System.out.println("Update Event button clicked");
    }

    @FXML
    private void handleDeleteEvent() {
        // Implement the logic to delete an event
        System.out.println("Delete Event button clicked");
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
}