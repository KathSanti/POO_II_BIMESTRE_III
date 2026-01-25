package com.speedfast.sistema.model;


import com.speedfast.sistema.capability.Despachable;

public abstract class Pedido implements Despachable {

    protected String idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected String repartidor;


    public Pedido(String idPedido, String direcccionEntrega, double distanciaKm, String repartidor) {
        this.idPedido = idPedido;
        this.direccionEntrega = direcccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidor = repartidor;
    }


    public abstract void asignarRepartidor();

    //Metodo sobrecargado

    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
        System.out.println("Repartidor " + nombre + " asignado manualmente al pedido #" + idPedido);
    }



    public void mostrarResumen(){
        System.out.println("========= Resumen Pedido ==========");
        System.out.println("ID pedido: " + idPedido);
        System.out.println("Dirección entrega: " + direccionEntrega);
        System.out.println("Repartidor: " + repartidor);
        System.out.println("Distancia: " + distanciaKm + "km");
        System.out.println("Tiempo de espera : " + calcularTiempodeEspera(distanciaKm) + " minutos");

        System.out.println("===================================\n");
    }

    public abstract double calcularTiempodeEspera(double distanciaKm);

    // Implementación base de las interfaces
    @Override
    public void despachar() {

        System.out.println("El pedido #" + idPedido + " ha salido a ruta.");
    }

    public String getIdPedido() {
        return idPedido;
    }

}
