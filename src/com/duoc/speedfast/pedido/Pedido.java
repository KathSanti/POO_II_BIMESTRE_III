package com.duoc.speedfast.pedido;

public abstract class Pedido {

    protected String idPedido;
    protected String direcccionEntrega;
    protected double distanciaKm;


    public Pedido(String idPedido, String direcccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direcccionEntrega = direcccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    //Metodo para agregar mas funciones

    public final void ejecutarPedido(){
        mostrarResumen();

    }

    public void mostrarResumen(){
        System.out.println("========= Resumen Pedido ==========");
        System.out.println("ID pedido: " + idPedido);
        System.out.println("Dirección entrega: " + direcccionEntrega);
        System.out.println("Distancia: " + distanciaKm + "km");
        System.out.println("Tiempo de espera : " + calcularTiempodeEspera(distanciaKm) + " minutos");

        System.out.println("===================================\n");
    }

    public abstract double calcularTiempodeEspera(double distanciaKm);


}
