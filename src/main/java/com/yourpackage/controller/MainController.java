package com.yourpackage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private void showUtilisateurView() {
        // Load and display the Utilisateur view
    }

    @FXML
    private void showEvenementView() {
        // Load and display the Événements view
    }

    @FXML
    private void handleSignIn() {
        loadFXML("/Signin.fxml");
    }

    @FXML
    private void handleSignUp() {
        loadFXML("/Signup.fxml");
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage if needed
            if (contentPane != null && contentPane.getScene() != null) {
                Stage currentStage = (Stage) contentPane.getScene().getWindow();
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}