package com.yourpackage.DAO;
import com.yourpackage.Model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements GenericDAO<Event> {
    private final String url = "jdbc:mysql://localhost:3306/EM";
    private final String username = "root";
    private final String password = "";

    @Override
    public void add(Event event) {
        String query = "INSERT INTO events (name, description, date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDate(3, new java.sql.Date(event.getDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Event get(int id) {
        String query = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date")
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
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void update(Event event) {
        String query = "UPDATE events SET name = ?, description = ?, date = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDate(3, new java.sql.Date(event.getDate().getTime()));
            stmt.setInt(4, event.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
