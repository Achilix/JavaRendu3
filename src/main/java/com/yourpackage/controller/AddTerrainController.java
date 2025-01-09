package com.yourpackage.controller;

import com.yourpackage.DAO.TerrainDAO;
import com.yourpackage.Model.Terrain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class AddTerrainController {

    @FXML
    private TextField terrainNameField;
    @FXML
    private TextField typeField;
    @FXML
    private ComboBox<String> deleteTerrainComboBox;

    private TerrainDAO terrainDao = new TerrainDAO();

    @FXML
    public void initialize() {
        loadTerrains();
    }

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

        loadTerrains();
    }

    @FXML
    private void handleDeleteTerrain() {
        String name = deleteTerrainComboBox.getValue();
        if (name == null || name.isEmpty()) {
            showAlert("Error", "Please select a Terrain to delete.");
            return;
        }

        Terrain terrain = terrainDao.getByName(name);
        if (terrain != null) {
            terrainDao.delete(terrain.getId());
            showAlert("Delete Terrain", "Terrain deleted successfully!");

            loadTerrains();
        } else {
            showAlert("Error", "Terrain not found.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadTerrains() {
        deleteTerrainComboBox.getItems().clear();
        List<Terrain> terrains = terrainDao.getAll();
        for (Terrain terrain : terrains) {
            deleteTerrainComboBox.getItems().add(terrain.getName());
        }
    }
}