package com.yourpackage.controller;

import com.yourpackage.DAO.TerrainDAO;
import com.yourpackage.Model.Terrain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AddTerrainController {

    @FXML
    private TextField terrainNameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField deleteTerrainNameField;
    @FXML
    private VBox contentArea;

    private TerrainDAO terrainDao = new TerrainDAO();

    @FXML
    private void handleAddTerrain() {
        String name = terrainNameField.getText();
        String type = typeField.getText();

        if (name.isEmpty() || type.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        Terrain terrain = new Terrain(0, name, type);
        terrainDao.add(terrain);

        showAlert("Add Terrain", "Terrain added successfully!");

        // Refresh the data in CreateEventController
        refreshCreateEventController();

        // Close the current stage
        Stage currentStage = (Stage) terrainNameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleDeleteTerrain() {
        String name = deleteTerrainNameField.getText();
        if (name.isEmpty()) {
            showAlert("Error", "Please enter the name of the Terrain to delete.");
            return;
        }

        Terrain terrain = terrainDao.getByName(name);
        if (terrain != null) {
            terrainDao.delete(terrain.getId());
            showAlert("Delete Terrain", "Terrain deleted successfully!");

            // Refresh the data in CreateEventController
            refreshCreateEventController();
        } else {
            showAlert("Error", "Terrain not found.");
        }

        // Close the current stage
        Stage currentStage = (Stage) deleteTerrainNameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void showCreateEvent() {
        loadView("/CreateEvent.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();
            contentArea.getChildren().setAll(node);
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

    private void refreshCreateEventController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateEvent.fxml"));
            Node node = loader.load();
            CreateEventController controller = loader.getController();
            controller.loadTerrains();
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}