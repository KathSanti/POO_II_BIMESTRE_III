package Speedfast.com.dao;

import Speedfast.com.controladores.Estado;
import Speedfast.com.controladores.TipoPedido;
import Speedfast.com.modelo.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void guardar (Pedido pedido) {
        String sql = "INSERT INTO pedido(direccion, tipo, estado) VALUES (?,?,?)";
        try(Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, pedido.getDireccionEntrega());
                ps.setString(2, pedido.getTipo().name());
                ps.setString(3, pedido.getEstado().name());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        pedido.setIdPedido(rs.getInt(1));

                    }
                }

        }catch (SQLException e){
                System.err.println("Error al guardar pedido: " + e.getMessage());
        }

    }

    public List<Pedido> listarTodos(){
        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT p.*, r.nombre AS repartidor FROM pedido p " +
                "LEFT JOIN entrega e ON p.id = e.id_pedido " +
                "LEFT JOIN repartidor r ON e.id_repartidor = r.id";


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
                pedido.setNombreRepartidor(rep != null ? rep : "Sin asignar ");
                lista.add(pedido);
            }

        }catch (SQLException e){
            System.err.println("Error en listar Pedido: " + e.getMessage());
        }
        return lista;
    }

    public void actualizarEstado(int idPedido, Estado estado) {
        String sql = "UPDATE pedido SET estado = ? Where id = ?";

        try(Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, estado.name());
            ps.setInt(2,idPedido);
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        }

    }

}
