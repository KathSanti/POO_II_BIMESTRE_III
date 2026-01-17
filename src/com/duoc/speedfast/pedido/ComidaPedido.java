package com.duoc.speedfast.pedido;

public class ComidaPedido extends Pedido {

    public ComidaPedido(String idPedido, String direcccionEntrega, double distanciaKm) {
        super(idPedido, direcccionEntrega, distanciaKm);
    }

    @Override
    public double calcularTiempodeEspera(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }

        return ( 15 * distanciaKm) + 2;
    }



}
