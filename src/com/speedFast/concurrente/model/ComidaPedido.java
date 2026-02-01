package com.speedFast.concurrente.model;

public class ComidaPedido extends Pedido {


    public ComidaPedido(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public void prepararDdespacho() {
        System.out.println("\nTipo de prepación : "+ " mochila termica confirmada" + "-" + "["+ idPedido +"]\n");

    }

    @Override
    public double calcularTiempodeEspera(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }

        return ( 15 + distanciaKm) + 2;
    }



}
