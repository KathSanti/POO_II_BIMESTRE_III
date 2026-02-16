package controladores;

import modelo.Pedido;

import java.util.concurrent.PriorityBlockingQueue;

public class ZonadeCarga {

    private final PriorityBlockingQueue<Pedido> colaPedido;


    public ZonadeCarga(PriorityBlockingQueue<Pedido> colaPedido) {
        this.colaPedido = colaPedido;
    }

    public synchronized void agregarPedido(Pedido pedido) {
        pedido.setEstado(Estado.PENDIENTE);
        colaPedido.put(pedido);
        System.out.println("[PEDIDO AGREGADO] " + pedido.getIdPedido() + " Destino : " + pedido.getDireccionEntrega()) ;

    }

    public synchronized Pedido retirarPedido() {

        return colaPedido.poll();
    }
}