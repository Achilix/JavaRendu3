package com.yourpackage.controller;

import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.Model.Salle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

        loadSalles();
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

            loadSalles();
        } else {
            showAlert("Error", "Salle not found.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadSalles() {
        deleteSalleComboBox.getItems().clear();
        List<Salle> salles = salleDao.getAll();
        for (Salle salle : salles) {
            deleteSalleComboBox.getItems().add(salle.getName());
        }
    }
}