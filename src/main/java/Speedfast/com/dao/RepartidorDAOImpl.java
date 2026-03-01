package Speedfast.com.dao;

import Speedfast.com.conexion.ConexionBD;
import Speedfast.com.interfacesDAO.RepartidorDAO;
import Speedfast.com.modelo.Repartidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAOImpl implements RepartidorDAO {

    @Override
    public void create(String nombre) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar repartidor: " + e.getMessage());
        }
    }

    @Override
    public List<Repartidor> readAll() {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT * FROM repartidores";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                repartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre"), null));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Repartidores: " + e.getMessage());
        }
        return repartidores;
    }

    @Override
    public void update(int id, String nuevoNombre) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar repartidor: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar repartidor: " + e.getMessage());
        }
    }
}