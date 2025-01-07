package com.yourpackage.DAO;

import com.yourpackage.Model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements GenericDAO<Event> {
    private final String url = "jdbc:postgresql://localhost:5432/EM";
    private final String username = "postgres";
    private final String password = "marouach123";

    @Override
    public void add(Event event) {
        String query = "INSERT INTO events (nom_event, description, date_event, id_user) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDate(3, new java.sql.Date(event.getDate().getTime()));
            stmt.setInt(4, event.getUserId());
            stmt.executeUpdate();

            // Retrieve the generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                event.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Event get(int id) {
        String query = "SELECT * FROM events WHERE id_event = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Event(
                        rs.getInt("id_event"),
                        rs.getString("nom_event"),
                        rs.getString("description"),
                        rs.getDate("date_event"),
                        rs.getInt("id_user")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id_event"),
                        rs.getString("nom_event"),
                        rs.getString("description"),
                        rs.getDate("date_event"),
                        rs.getInt("id_user")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void update(Event event) {
        String query = "UPDATE events SET nom_event = ?, description = ?, date_event = ?, id_user = ? WHERE id_event = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDate(3, new java.sql.Date(event.getDate().getTime()));
            stmt.setInt(4, event.getUserId());
            stmt.setInt(5, event.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM events WHERE id_event = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}