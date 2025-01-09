package com.yourpackage.controller;

import com.yourpackage.DAO.UserDAO;
import com.yourpackage.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SignupController {

    private final UserDAO userDao;

    public SignupController() {
        this.userDao = new UserDAO();
    }

    public SignupController(UserDAO userDao) {
        this.userDao = userDao;
    }

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleSignUp() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        String type = "Student";

        User newUser = new User(0, nom, prenom, email, password, type); 
        userDao.add(newUser);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText("Sign Up successful!");
        alert.showAndWait();
    }
}