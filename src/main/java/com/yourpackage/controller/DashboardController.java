package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.DAO.ReservationDAO;
import com.yourpackage.DAO.SalleDAO;
import com.yourpackage.DAO.TerrainDAO;
import com.yourpackage.Model.Event;
import com.yourpackage.Model.Reservation;
import com.yourpackage.Model.Salle;
import com.yourpackage.Model.Terrain;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class DashboardController {

    private final EventDAO eventDao = new EventDAO();
    private final ReservationDAO reservationDao = new ReservationDAO();
    private final SalleDAO salleDao = new SalleDAO();
    private final TerrainDAO terrainDao = new TerrainDAO();

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, Integer> idColumn;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> descriptionColumn;

    

    @FXML
    private TableColumn<Event, String> reservedSalleColumn;

    @FXML
    private TableColumn<Event, String> reservedTerrainColumn;

    @FXML
    private TableColumn<Event, Date> reservedDateColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        reservedSalleColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            Reservation reservation = reservationDao.getReservationByEventId(event.getId());
            if (reservation != null && reservation.getSalleId() != 0) {
                Salle salle = salleDao.getById(reservation.getSalleId());
                return salle != null ? new SimpleStringProperty(salle.getName()) : null;
            }
            return null;
        });

        reservedTerrainColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            Reservation reservation = reservationDao.getReservationByEventId(event.getId());
            if (reservation != null && reservation.getTerrainId() != 0) {
                Terrain terrain = terrainDao.getById(reservation.getTerrainId());
                return terrain != null ? new SimpleStringProperty(terrain.getName()) : null;
            }
            return null;
        });

        reservedDateColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            Reservation reservation = reservationDao.getReservationByEventId(event.getId());
            if (reservation != null) {
                java.util.Date utilDate = reservation.getReservationDate();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                return new SimpleObjectProperty<>(sqlDate);
            }
            return null;
        });

        loadAllEvents();
    }

    private void loadAllEvents() {
        List<Event> events = eventDao.getAll();
        eventsTableView.getItems().setAll(events);
    }

    @FXML
    private void handleCreateEvent() {
        loadFXML("/MainWindow.fxml");
    }

    @FXML
    private void handleUpdateEvent() {
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
                Parent root = loader.load();

                UpdateEventController controller = loader.getController();
                controller.setEvent(selectedEvent);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                loadAllEvents();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select an event to update.");
        }
    }

    @FXML
    private void handleDeleteEvent() {
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventDao.delete(selectedEvent.getId());
            eventsTableView.getItems().remove(selectedEvent);
        } else {
            showAlert("No Selection", "Please select an event to delete.");
        }
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
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
}