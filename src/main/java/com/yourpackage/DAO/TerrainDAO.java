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
                terrains.add(new Terrain(
                        rs.getInt("id_terrain"),
                        rs.getString("nom_terrain"),
                        rs.getString("type"),
                        rs.getInt("size")
                ));
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
                        rs.getString("type"),
                        rs.getInt("size")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Terrain terrain) {
        String query = "INSERT INTO terrains (nom_terrain, type, size) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, terrain.getName());
            stmt.setString(2, terrain.getType());
            stmt.setInt(3, terrain.getSize());
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
}