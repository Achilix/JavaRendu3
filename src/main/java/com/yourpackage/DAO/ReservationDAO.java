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
        String query = "INSERT INTO reservations (id_user, id_event, id_salle, id_terrain, reservation_date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getEventId());
            stmt.setInt(3, reservation.getSalleId());
            stmt.setInt(4, reservation.getTerrainId());
            stmt.setDate(5, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setTime(6, reservation.getStartTime());
            stmt.setTime(7, reservation.getEndTime());
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
                        rs.getDate("reservation_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time")
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
                        rs.getDate("reservation_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void update(Reservation reservation) {
        String query = "UPDATE reservations SET id_user = ?, id_event = ?, id_salle = ?, id_terrain = ?, reservation_date = ?, start_time = ?, end_time = ? WHERE id_reservation = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getEventId());
            stmt.setInt(3, reservation.getSalleId());
            stmt.setInt(4, reservation.getTerrainId());
            stmt.setDate(5, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setTime(6, reservation.getStartTime());
            stmt.setTime(7, reservation.getEndTime());
            stmt.setInt(8, reservation.getId());
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
}