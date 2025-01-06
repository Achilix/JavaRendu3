package com.yourpackage.controller;

import com.yourpackage.DAO.ReservationDAO;
import com.yourpackage.Model.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage; // Add this import statement

import java.sql.Date;

public class ReserveSalleController {

    private final ReservationDAO reservationDao = new ReservationDAO();

    @FXML
    private TextField salleIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField dateField;

    @FXML
    private void handleReserve() {
        int salleId = Integer.parseInt(salleIdField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        Date date = Date.valueOf(dateField.getText());

        Reservation reservation = new Reservation(0, userId, salleId, date);
        reservationDao.add(reservation);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reserve Salle");
        alert.setHeaderText(null);
        alert.setContentText("Salle reserved successfully!");
        alert.showAndWait();

        // Close the current stage
        Stage currentStage = (Stage) salleIdField.getScene().getWindow();
        currentStage.close();
    }
}