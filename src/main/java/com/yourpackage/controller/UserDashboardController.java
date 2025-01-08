package com.yourpackage.controller;

import com.yourpackage.DAO.EventDAO;
import com.yourpackage.Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.Date;

import java.util.List;

public class UserDashboardController {

    private final EventDAO eventDao = new EventDAO();

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, Integer> idColumn;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> descriptionColumn;

    @FXML
    private TableColumn<Event, Date> dateColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadAvailableEvents();
    }

    private void loadAvailableEvents() {
        List<Event> events = eventDao.getAll();
        eventsTableView.getItems().setAll(events);
    }

    @FXML
    private void showMainView() {
        Stage currentStage = (Stage) eventsTableView.getScene().getWindow();
        currentStage.close();
    }
}