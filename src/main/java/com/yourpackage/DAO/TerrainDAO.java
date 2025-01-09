package com.yourpackage.DAO;

import com.yourpackage.Model.Terrain;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainDAO {
    private final String url = "jdbc:postgresql://localhost:5432/EM";
    private final String username = "postgres";
    private final String password = "marouach123";

    public List<Terrain> getAll() {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrains";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Terrain terrain = new Terrain(
                        rs.getInt("id_terrain"),
                        rs.getString("nom_terrain"),
                        rs.getString("type")
                );
                terrains.add(terrain);
                System.out.println("Fetched Terrain: " + terrain); // Debugging statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    public Terrain getByName(String name) {
        String query = "SELECT * FROM terrains WHERE nom_terrain = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Terrain(
                        rs.getInt("id_terrain"),
                        rs.getString("nom_terrain"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Terrain terrain) {
        String query = "INSERT INTO terrains (nom_terrain, type) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, terrain.getName());
            stmt.setString(2, terrain.getType());
            stmt.executeUpdate();

            // Retrieve the generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                terrain.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM terrains WHERE id_terrain = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Terrain getById(int id) {
        Terrain terrain = null;
        String query = "SELECT * FROM terrains WHERE id_terrain = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                terrain = new Terrain(
                    resultSet.getInt("id_terrain"),
                    resultSet.getString("nom_terrain"),
                    resultSet.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrain;
    }
}