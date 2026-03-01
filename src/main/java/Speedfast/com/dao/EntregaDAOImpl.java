package Speedfast.com.dao;

import Speedfast.com.conexion.ConexionBD;
import Speedfast.com.interfacesDAO.EntregaDAO;
import Speedfast.com.modelo.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAOImpl implements EntregaDAO {

    @Override
    public void create(Entrega entrega) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar entrega: " + e.getMessage());
        }
    }

    @Override
    public List<Entrega> readAll() {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM entregas";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Entrega entrega = new Entrega(
                        rs.getInt("id"),             // caputuramos la información de la tabla entregas
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha"),
                        rs.getTime("hora")
                );
                entregas.add(entrega);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }
        return entregas;
    }

    @Override
    public void update(Entrega entrega) {
        String sql = "UPDATE entregas SET id_repartidor = ?, fecha = ?, hora = ? WHERE id_pedido = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdRepartidor());
            ps.setDate(2, entrega.getFecha());
            ps.setTime(3, entrega.getHora());
            ps.setInt(4, entrega.getIdPedido());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar entrega: " + e.getMessage());
        }
    }

    @Override
    public void delete(int idPedido) {
        String sql = "DELETE FROM entregas WHERE id_pedido = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
        }
    }
}