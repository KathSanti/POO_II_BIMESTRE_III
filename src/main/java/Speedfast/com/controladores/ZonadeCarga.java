package Speedfast.com.controladores;

import Speedfast.com.modelo.Pedido;

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
        try {
            return colaPedido.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}