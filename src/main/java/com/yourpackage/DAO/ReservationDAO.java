package com.yourpackage.DAO;

import com.yourpackage.Model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO implements GenericDAO<Reservation> {
    private final String url = "jdbc:mysql://localhost:3306/EM";
    private final String username = "root";
    private final String password = "";

    @Override
    public void add(Reservation reservation) {
        String query = "INSERT INTO reservations (user_id, resource_id, reservation_date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getResourceId());
            stmt.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation get(int id) {
        String query = "SELECT * FROM reservations WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("resource_id"),
                        rs.getDate("reservation_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("resource_id"),
                        rs.getDate("reservation_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void update(Reservation reservation) {
        String query = "UPDATE reservations SET user_id = ?, resource_id = ?, reservation_date = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getResourceId());
            stmt.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setInt(4, reservation.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reservations WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
