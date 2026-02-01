package com.speedFast.concurrente.model;

public class ExpressPedido extends Pedido{

    public ExpressPedido(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public void prepararDdespacho() {
        System.out.println("\nTipo de prepación : "+ " rayomacqueen" + "-" + "["+ idPedido +"]\n");
    }


    @Override
    public double calcularTiempodeEspera(double distanciaKm) {

        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        } else if (distanciaKm > 5) {
            return 10 * distanciaKm + 5;

        }

        return 10 * distanciaKm;

    }
}
