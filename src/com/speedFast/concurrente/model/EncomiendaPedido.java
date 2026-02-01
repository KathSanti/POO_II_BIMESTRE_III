package com.speedFast.concurrente.model;

public class EncomiendaPedido extends Pedido {


    public EncomiendaPedido(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public void despacho() {
        System.out.println("\nTipo de prepación : "+ " embalaje especial" + "-" + "["+ idPedido +"]\n");
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
