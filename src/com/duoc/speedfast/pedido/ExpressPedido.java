package com.duoc.speedfast.pedido;

public class ExpressPedido extends Pedido{

    public ExpressPedido(String idPedido, String direcccionEntrega, double distanciaKm) {
        super(idPedido, direcccionEntrega, distanciaKm);
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
