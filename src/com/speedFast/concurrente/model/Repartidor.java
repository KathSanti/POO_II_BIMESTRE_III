package com.speedFast.concurrente.model;

import java.util.List;

public class Repartidor implements Runnable{

    private String nombreRepartidor;
    private List<Pedido> pedidosAsignados;

    public Repartidor(String nombreRepartidor, List<Pedido> pedidosAsignados){
        this.nombreRepartidor = nombreRepartidor;
        this.pedidosAsignados = pedidosAsignados;
    }

    @Override
    public void run() {

        for (Pedido pedido : pedidosAsignados) {

            System.out.println("[ Repartidor : " + nombreRepartidor + "] fue asignado y esta entregando el pedido " + pedido.getIdPedido());

            // Vincular el repartidor con Pedido
            pedido.asignarRepartidor(this.nombreRepartidor);

            //Ejecutar el despacho del pedido de acuerdo al tipo
            pedido.despacho();

            try {

                double tiempoTeoricoEnMinutos = pedido.calcularTiempodeEspera(pedido.getDistanciaKm());
                System.out.println("(Tiempo estimado de viaje: " + tiempoTeoricoEnMinutos + " para el pedido: " + pedido.getIdPedido());
                long tiempoSimulacionMilisegundos = (long) (tiempoTeoricoEnMinutos * 1000);

                Thread.sleep(tiempoSimulacionMilisegundos);



            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("[ Repartidor : " + nombreRepartidor + "] Ha entregado : " + pedido.getIdPedido() + "\n");


        }
    }


}
