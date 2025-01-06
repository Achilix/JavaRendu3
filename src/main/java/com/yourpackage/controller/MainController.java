package com.yourpackage.controller;

import com.yourpackage.DAO.UserDAO;
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

    private final UserDAO userDao = new UserDAO();

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
        loadFXML("/Signin.fxml", new SigninController(userDao));
    }

    @FXML
    private void handleSignUp() {
        loadFXML("/Signup.fxml", new SignupController(userDao));
    }

    private void loadFXML(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}