package com.duoc.speedFast;

public class PedidoExpress extends Pedido {

    private String tiendaPedido;
    private boolean requiereInmediato;



    public PedidoExpress(String idPedido, String direccionEntrega, String tipoPedido, String tiendaPedido,boolean repartidorinmediato) {
        super(idPedido, direccionEntrega, tipoPedido);
        this.tiendaPedido = tiendaPedido;
        this.requiereInmediato = repartidorinmediato;

    }


    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println("\n========== Asignación Compra Express ===========");
        System.out.println("Pedido: " + idPedido);
        System.out.println("Articulo :" + tipoPedido) ;
        System.out.println("Tienda: " + tiendaPedido);
        System.out.println("Repartidor propuesto: " + nombreRepartidor);

        System.out.println("Verificando ubicación y disponibilidad de " + nombreRepartidor + "...");

        System.out.println("\nPedido express estándar");
        System.out.println(nombreRepartidor + " asignado para compra express");

        System.out.println("============================================\n");


    }


}
