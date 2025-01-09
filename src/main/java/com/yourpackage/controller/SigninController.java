package com.yourpackage.controller;

import com.yourpackage.DAO.UserDAO;
import com.yourpackage.Model.User;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Task<Boolean> signInTask = new Task<>() {
            @Override
            protected Boolean call() {
                return userDao.validateUser(email, password);
            }
        };

        signInTask.setOnSucceeded(event -> {
            boolean isAuthenticated = signInTask.getValue();
            if (isAuthenticated) {
                User user = userDao.getByEmail(email);
                userId = user.getId(); 
                String userType = user.getType();

                if ("Student".equals(userType)) {
                    showUserDashboard();
                } else if ("professeur".equals(userType)) {
                    showDashboard();
                }

                Stage currentStage = (Stage) emailField.getScene().getWindow();
                currentStage.close();
            }
        });

        new Thread(signInTask).start();
    }

    private void showUserDashboard() {
        openNewWindow("/UserDashboard.fxml", "User Dashboard");
    }

    private void showDashboard() {
        openNewWindow("/MainWindow.fxml", "Dashboard");
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

    public int getUserId() {
        return userId;
    }
}