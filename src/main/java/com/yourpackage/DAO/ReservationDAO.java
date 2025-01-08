package com.yourpackage.DAO;

import com.yourpackage.Model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO implements GenericDAO<Reservation> {
    private final String url = "jdbc:postgresql://localhost:5432/EM";
    private final String username = "postgres";
    private final String password = "marouach123";

    @Override
    public void add(Reservation reservation) {
        String query = "INSERT INTO reservations (id_user, id_event, id_salle, id_terrain, reservation_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getEventId());
            stmt.setInt(3, reservation.getSalleId());
            stmt.setInt(4, reservation.getTerrainId());
            stmt.setDate(5, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation get(int id) {
        String query = "SELECT * FROM reservations WHERE id_reservation = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id_reservation"),
                        rs.getInt("id_user"),
                        rs.getInt("id_event"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_terrain"),
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
                        rs.getInt("id_reservation"),
                        rs.getInt("id_user"),
                        rs.getInt("id_event"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_terrain"),
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
        String query = "UPDATE reservations SET id_user = ?, id_event = ?, id_salle = ?, id_terrain = ?, reservation_date = ? WHERE id_reservation = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getEventId());
            stmt.setInt(3, reservation.getSalleId());
            stmt.setInt(4, reservation.getTerrainId());
            stmt.setDate(5, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setInt(6, reservation.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reservations WHERE id_reservation = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSalleReserved(int salleId, Date date) {
        String query = "SELECT COUNT(*) FROM reservations WHERE id_salle = ? AND reservation_date = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, salleId);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTerrainReserved(int terrainId, Date date) {
        String query = "SELECT COUNT(*) FROM reservations WHERE id_terrain = ? AND reservation_date = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, terrainId);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}