package com.yourpackage.controller;

import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.Model.Salle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class AddSalleController {

    private final SalleDAO salleDao = new SalleDAO();

    @FXML
    private TextField salleNameField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField locationField;

    @FXML
    private VBox contentArea;

    @FXML
    private void handleAddSalle() {
        String name = salleNameField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        String location = locationField.getText();

        Salle salle = new Salle(0, name, capacity, location);
        salleDao.add(salle);

        showAlert("Add Salle", "Salle added successfully!");

        // Close the current stage
        Stage currentStage = (Stage) salleNameField.getScene().getWindow();
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