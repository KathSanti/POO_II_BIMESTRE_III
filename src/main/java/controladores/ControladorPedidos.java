package controladores;

import modelo.Pedido;
import modelo.Repartidor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class ControladorPedidos {

    private ZonadeCarga zonadeCarga;

    private List<Pedido> listaPedidosVisual;


    public ControladorPedidos() {
        // Inicializamos la cola concurrente y la zona de carga
        PriorityBlockingQueue<Pedido> cola = new PriorityBlockingQueue<>();
        this.zonadeCarga = new ZonadeCarga(cola);
        this.listaPedidosVisual = new ArrayList<>();
    }

    public void registrarPedido(int id, String direccion,TipoPedido tipo){
        Pedido nuevoP = new Pedido(id, direccion,tipo, Estado.PENDIENTE);
        zonadeCarga.agregarPedido(nuevoP);
        listaPedidosVisual.add(nuevoP);
    }

    // Metodo para llenar la JTable
    public List<Pedido> obtenerPedidos() {
        return listaPedidosVisual;
    }

    // Metodo llamado por el Botón "Iniciar Entrega"
    public void iniciarEntregas() {
        // Aquí creamos los hilos repartidores u
        Repartidor r1 = new Repartidor("Juan Perez", zonadeCarga);
        Repartidor r2 = new Repartidor("María Gerrero", zonadeCarga);

        new Thread(r1).start();
        new Thread(r2).start();
    }




}
