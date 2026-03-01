package Speedfast.com.interfacesDAO;


import Speedfast.com.modelo.Pedido;
import java.util.List;

public interface PedidoDAO {
    void create(Pedido pedido);
    List<Pedido> readAll();
    void update(Pedido pedido);
    void delete(int id);
}