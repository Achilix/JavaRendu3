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

    private final TerrainDAO terrainDao = new TerrainDAO();

    @FXML
    private TextField terrainNameField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField sizeField;

    @FXML
    private VBox contentArea;

    @FXML
    private void handleAddTerrain() {
        String name = terrainNameField.getText();
        String type = typeField.getText();
        int size = Integer.parseInt(sizeField.getText());

        Terrain terrain = new Terrain(0, name, type, size);
        terrainDao.add(terrain);

        showAlert("Add Terrain", "Terrain added successfully!");

        // Close the current stage
        Stage currentStage = (Stage) terrainNameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void showCreateEvent() {
        loadView("/com/yourpackage/view/CreateEvent.fxml");
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
}