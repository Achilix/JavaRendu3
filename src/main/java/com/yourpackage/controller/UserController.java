package com.yourpackage.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Add your submission logic here

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Submission");
        alert.setHeaderText(null);
        alert.setContentText("Submission successful!");
        alert.showAndWait();
    }
}