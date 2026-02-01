package com.speedFast.concurrente.model;


import com.speedFast.concurrente.capability.Despachable;

public abstract class Pedido implements Despachable {

    protected String idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected String repartidor;


    public Pedido(String idPedido, String direcccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direcccionEntrega;
        this.distanciaKm = distanciaKm;

    }


    //Metodo abstracto para separar el tipo de preparación del despacho


    public abstract void prepararDdespacho();

    //Metodo para asignar nombre de repartidor al pedido de acuerdo a la información en main desde la clase repartidor

    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;

    }



    public void mostrarResumen(){
        System.out.println("========= Resumen Pedido ==========");
        System.out.println("ID pedido: " + idPedido);
        System.out.println("Repartidor: " + repartidor);
        System.out.println("Dirección entrega: " + direccionEntrega);
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

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }


}
