package com.yourpackage.DAO;
import com.yourpackage.Model.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO implements GenericDAO<Salle> {
    private final String url = "jdbc:mysql://localhost:3306/EM";
    private final String username = "root";
    private final String password = "";

    @Override
    public void add(Salle salle) {
        String query = "INSERT INTO salles (name, capacity, location) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, salle.getName());
            stmt.setInt(2, salle.getCapacity());
            stmt.setString(3, salle.getLocation());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Salle get(int id) {
        String query = "SELECT * FROM salles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Salle(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Salle> getAll() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salles";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                salles.add(new Salle(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }

    @Override
    public void update(Salle salle) {
        String query = "UPDATE salles SET name = ?, capacity = ?, location = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, salle.getName());
            stmt.setInt(2, salle.getCapacity());
            stmt.setString(3, salle.getLocation());
            stmt.setInt(4, salle.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM salles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
