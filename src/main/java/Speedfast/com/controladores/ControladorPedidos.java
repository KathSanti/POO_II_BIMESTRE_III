package Speedfast.com.controladores;

import Speedfast.com.dao.PedidoDAO;
import Speedfast.com.dao.RepartidorDAO;
import Speedfast.com.modelo.Pedido;
import Speedfast.com.modelo.Repartidor;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class ControladorPedidos {
    private ZonadeCarga zonadeCarga;
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private RepartidorDAO repartidorDAO = new RepartidorDAO();

    public ControladorPedidos() {
        PriorityBlockingQueue<Pedido> cola = new PriorityBlockingQueue<>();
        this.zonadeCarga = new ZonadeCarga(cola);

        cargarPendientesDesdeBD();
    }

    // Metodo que busca en MySQL y llena la cola con lo que quedó pendiente
    private void cargarPendientesDesdeBD() {
        List<Pedido> todosLosPedidos = pedidoDAO.listarTodos();
        int contador = 0;

        for (Pedido p : todosLosPedidos) {
            // Si en la base de datos dice que está PENDIENTE, lo metemos a la cola
            if (p.getEstado() == Estado.PENDIENTE) {
                zonadeCarga.agregarPedido(p);
                contador++;
            }
        }

        System.out.println(">>> INFO: Se recuperaron " + contador + " pedidos pendientes antiguos desde la BD.");
    }

    public void registrarPedido(String direccion, TipoPedido tipo){
        Pedido nuevoP = new Pedido(0, direccion, tipo, Estado.PENDIENTE);
        pedidoDAO.guardar(nuevoP); // Guarda en MySQL
        zonadeCarga.agregarPedido(nuevoP); // Lo manda a la cola de hilos
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listarTodos();
    }

    public void iniciarEntregas() {
        List<Repartidor> repartidores = repartidorDAO.listarRepartidores(zonadeCarga);
        System.out.println(">>> ATENCIÓN: Se encontraron " + repartidores.size() + " repartidores en la BD.");

        for (Repartidor r : repartidores) {
            new Thread(r).start();
        }
    }
}