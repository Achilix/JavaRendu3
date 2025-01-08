package com.yourpackage.controller;

import com.yourpackage.DAO.UserDAO;
import com.yourpackage.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SigninController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDao = new UserDAO();
    private int userId; // Store the user ID

    @FXML
    private void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate user credentials
        boolean isAuthenticated = userDao.validateUser(email, password);

        if (isAuthenticated) {
            User user = userDao.getByEmail(email);
            if (user != null) {
                userId = user.getId(); // Store the user ID
                String userType = user.getType();

                if ("Student".equals(userType)) {
                    showUserDashboard();
                } else if ("professeur".equals(userType)) {
                    showDashboard();
                } else {
                    showAlert("Error", "Unknown user type.");
                }

                // Close the current stage
                Stage currentStage = (Stage) emailField.getScene().getWindow();
                currentStage.close();
            } else {
                showAlert("Error", "User not found.");
            }
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }

    private void showUserDashboard() {
        openNewWindow("/UserDashboard.fxml", "User Dashboard");
    }

    private void showDashboard() {
        openNewWindow("/Dashboard.fxml", "Dashboard");
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public int getUserId() {
        return userId;
    }
}