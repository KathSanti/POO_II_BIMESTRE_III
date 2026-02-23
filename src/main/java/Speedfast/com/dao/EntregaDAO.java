package Speedfast.com.dao;

import Speedfast.com.modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {

    // Registra la relación entre pedido y repartidor
    public void guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
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


}
