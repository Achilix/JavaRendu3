package com.yourpackage.controller;

import com.yourpackage.DAO.ReservationDAO;
import com.yourpackage.Model.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Date;

public class ReserveTerrainController {

    private final ReservationDAO reservationDao = new ReservationDAO();

    @FXML
    private TextField terrainIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField dateField;

    @FXML
    private void handleReserve() {
        int terrainId = Integer.parseInt(terrainIdField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        Date date = Date.valueOf(dateField.getText());

        Reservation reservation = new Reservation(0, userId, terrainId, date);
        reservationDao.add(reservation);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reserve Terrain");
        alert.setHeaderText(null);
        alert.setContentText("Terrain reserved successfully!");
        alert.showAndWait();

        // Close the current stage
        Stage currentStage = (Stage) terrainIdField.getScene().getWindow();
        currentStage.close();
    }
}