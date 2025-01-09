package com.yourpackage.controller;

import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.Model.Salle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddSalleController {

    private final SalleDAO salleDao = new SalleDAO();

    @FXML
    private TextField salleNameField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> deleteSalleComboBox;

    @FXML
    private VBox contentArea;

    @FXML
    public void initialize() {
        loadSalles();
    }

    @FXML
    private void handleAddSalle() {
        String name = salleNameField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        String location = locationField.getText();

        Salle salle = new Salle(0, name, capacity, location);
        salleDao.add(salle);

        showAlert("Add Salle", "Salle added successfully!");

        // Refresh the data in CreateEventController
        refreshCreateEventController();

        // Close the current stage
        Stage currentStage = (Stage) salleNameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleDeleteSalle() {
        String name = deleteSalleComboBox.getValue();
        if (name == null || name.isEmpty()) {
            showAlert("Error", "Please select a Salle to delete.");
            return;
        }

        Salle salle = salleDao.getByName(name);
        if (salle != null) {
            salleDao.delete(salle.getId());
            showAlert("Delete Salle", "Salle deleted successfully!");

            // Refresh the data in CreateEventController
            refreshCreateEventController();
        } else {
            showAlert("Error", "Salle not found.");
        }

        // Close the current stage
        Stage currentStage = (Stage) deleteSalleComboBox.getScene().getWindow();
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
            controller.loadSalles();
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSalles() {
        List<Salle> salles = salleDao.getAll();
        for (Salle salle : salles) {
            deleteSalleComboBox.getItems().add(salle.getName());
        }
    }
}