package com.speedfast.sistema.model;

public class ExpressPedido extends Pedido{

    public ExpressPedido(String idPedido, String direccionEntrega, double distanciaKm, String repartidor) {
        super(idPedido, direccionEntrega, distanciaKm, repartidor);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("\nRepartidor asignado : " + repartidor + ", se dirije a lo rayomacqueen\n");
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
