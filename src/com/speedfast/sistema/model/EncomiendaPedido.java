package com.speedfast.sistema.model;

public class EncomiendaPedido extends Pedido {


    public EncomiendaPedido(String idPedido, String direccionEntrega, double distanciaKm, String repartidor) {
        super(idPedido, direccionEntrega, distanciaKm, repartidor);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("\nRepartidor asignado : " + repartidor + ", pedido con embalaje especial\n");
    }


    @Override
    public double calcularTiempodeEspera(double distanciaKm) {

        if (distanciaKm < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }


        // Le pedimos al compilador que retorne el valor de un entero long para que el compilador haga casting y lo convierta en double
        return Math.round(20+(1.5*distanciaKm));
    }


}
