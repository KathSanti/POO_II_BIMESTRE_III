package Speedfast.com.dao;

import Speedfast.com.conexion.ConexionBD;
import Speedfast.com.controladores.Estado;
import Speedfast.com.controladores.TipoPedido;
import Speedfast.com.interfacesDAO.PedidoDAO;
import Speedfast.com.modelo.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public void create(Pedido pedido) {
        String sql = "INSERT INTO pedidos(direccion, tipo, estado) VALUES (?,?,?)";
        try(Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();

            // Recuperamos el ID generado
            try (ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    pedido.setIdPedido(rs.getInt(1));
                }
            }
        } catch (SQLException e){
            System.err.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> readAll() {
        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT p.id, p.direccion, p.tipo, p.estado, " +
                "(SELECT r.nombre FROM entregas e JOIN repartidores r ON e.id_repartidor = r.id WHERE e.id_pedido = p.id LIMIT 1) AS repartidor " +
                "FROM pedidos p";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        Estado.valueOf(rs.getString("estado"))
                );
                String rep = rs.getString("repartidor");
                pedido.setNombreRepartidor(rep != null ? rep : "Sin asignar");
                lista.add(pedido);
            }
        } catch (SQLException e){
            System.err.println("Error en listar Pedidos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";
        try(Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getIdPedido());
            ps.executeUpdate();

        } catch (SQLException e){
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se puede eliminar: El pedido ya tiene una entrega asignada.");
        }
    }
}
