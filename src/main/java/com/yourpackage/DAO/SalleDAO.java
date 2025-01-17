package com.yourpackage.DAO;

import com.yourpackage.Model.Salle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {
    private final String url = "jdbc:postgresql://localhost:5432/EM";
    private final String username = "postgres";
    private final String password = "marouach123";

    public List<Salle> getAll() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salles";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Salle salle = new Salle(
                        rs.getInt("id_salles"),
                        rs.getString("nom_salle"),
                        rs.getInt("capacity"),
                        rs.getString("location")
                );
                salles.add(salle);
                System.out.println("Fetched Salle: " + salle); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }

    public Salle getByName(String name) {
        String query = "SELECT * FROM salles WHERE nom_salle = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Salle(
                        rs.getInt("id_salles"),
                        rs.getString("nom_salle"),
                        rs.getInt("capacity"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Salle salle) {
        String query = "INSERT INTO salles (nom_salle, capacity, location) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, salle.getName());
            stmt.setInt(2, salle.getCapacity());
            stmt.setString(3, salle.getLocation());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                salle.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM salles WHERE id_salles = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Salle getById(int id) {
        Salle salle = null;
        String query = "SELECT * FROM salles WHERE id_salles = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salle = new Salle(
                    resultSet.getInt("id_salles"),
                    resultSet.getString("nom_salle"),
                    resultSet.getInt("capacity"),
                    resultSet.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle;
    }
}