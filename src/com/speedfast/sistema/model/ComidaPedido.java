package com.speedfast.sistema.model;

public class ComidaPedido extends Pedido {


    public ComidaPedido(String idPedido, String direccionEntrega, double distanciaKm, String repartidor) {
        super(idPedido, direccionEntrega, distanciaKm, repartidor);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("\nRepartidor asignado : " + repartidor + ", mochila termica confirmada\n");

    }

    @Override
    public double calcularTiempodeEspera(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }

        return ( 15 + distanciaKm) + 2;
    }



}
