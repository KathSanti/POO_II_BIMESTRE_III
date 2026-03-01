package Speedfast.com.controladores;

import Speedfast.com.interfacesDAO.PedidoDAO;
import Speedfast.com.dao.PedidoDAOImpl;
import Speedfast.com.dao.RepartidorDAOImpl;
import Speedfast.com.interfacesDAO.RepartidorDAO;
import Speedfast.com.modelo.Pedido;
import Speedfast.com.modelo.Repartidor;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;


public class ControladorPedidos {

    private ZonadeCarga zonadeCarga;
    private PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private RepartidorDAO repartidorDAO = new RepartidorDAOImpl();

    public ControladorPedidos() {
        PriorityBlockingQueue<Pedido> cola = new PriorityBlockingQueue<>();
        this.zonadeCarga = new ZonadeCarga(cola);

        cargarPendientesDesdeBD();
    }

    private void cargarPendientesDesdeBD() {
        List<Pedido> todosLosPedidos = pedidoDAO.readAll();
        int contador = 0;

        for (Pedido p : todosLosPedidos) {
            if (p.getEstado() == Estado.PENDIENTE) {
                zonadeCarga.agregarPedido(p);
                contador++;
            }
        }

        System.out.println(">>> INFO: Se recuperaron " + contador + " pedidos pendientes antiguos desde la BD.");
    }

    public void registrarPedido(String direccion, TipoPedido tipo){
        Pedido nuevoP = new Pedido(0, direccion, tipo, Estado.PENDIENTE);

        //actaluzación crud
        pedidoDAO.create(nuevoP);

        zonadeCarga.agregarPedido(nuevoP);
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.readAll();
    }

    public void iniciarEntregas() {
        // cambio Llamar a readAll sin parámetros
        List<Repartidor> repartidores = repartidorDAO.readAll();
        System.out.println(">>> ATENCIÓN: Se encontraron " + repartidores.size() + " repartidores en la BD.");

        for (Repartidor r : repartidores) {

            r.setZonadeCarga(this.zonadeCarga);

            new Thread(r).start();
        }
    }
}