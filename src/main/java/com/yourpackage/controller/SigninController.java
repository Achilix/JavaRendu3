package com.yourpackage.controller;

import com.yourpackage.DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class SigninController {

    private final UserDAO userDao;

    // No-argument constructor
    public SigninController() {
        this.userDao = new UserDAO(); // Initialize userDao or handle it appropriately
    }

    public SigninController(UserDAO userDao) {
        this.userDao = userDao;
    }

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Attempting to sign in with email: " + email + " and password: " + password);

        boolean success = userDao.validateUser(email, password);

        Alert alert = new Alert(success ? AlertType.INFORMATION : AlertType.ERROR);
        alert.setTitle("Sign In");
        alert.setHeaderText(null);
        alert.setContentText(success ? "Sign In successful!" : "Sign In failed!");
        alert.showAndWait();

        if (success) {
            loadCreateEvent();
        }
    }

    private void loadCreateEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}