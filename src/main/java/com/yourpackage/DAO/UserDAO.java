package com.yourpackage.DAO;

import com.yourpackage.Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements GenericDAO<User> {
    private final String url = "jdbc:postgresql://localhost:5432/EM";
    private final String username = "postgres";
    private final String password = "marouach123";

    @Override
    public void add(User user) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, password, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getType() != null ? user.getType() : "Student");
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); 
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while adding user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public User get(int id) {
        String query = "SELECT * FROM utilisateurs WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("password"), rs.getString("type"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while retrieving user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public User getByEmail(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("password"), rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("password"), rs.getString("type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, password = ?, type = ? WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getType());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM utilisateurs WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateUser(String email, String password) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            System.out.println("Executing query: " + stmt);
            ResultSet rs = stmt.executeQuery();
            boolean result = rs.next();
            System.out.println("User validation result: " + result);
            return result;
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while validating user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}