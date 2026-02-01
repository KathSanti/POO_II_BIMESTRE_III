package com.speedFast.concurrente.app;


import com.speedFast.concurrente.controlador.ControladorEnvios;
import com.speedFast.concurrente.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {

        ControladorEnvios controlador = new ControladorEnvios(new ArrayList<>());

        Pedido p1 = new ComidaPedido("001", "Viña del mar 557, valparaiso",7);
        Pedido p2= new ExpressPedido("002", "Las Margaritas, 2470, Macul", 4);
        Pedido p3= new EncomiendaPedido("003","Zennit 1470, Maitenes", 19);


        controlador.recibirPedido(p1);
        controlador.recibirPedido(p2);
        controlador.recibirPedido(p3);

        //Listas individuales para cada repartidor

        List<Pedido> repartidorMaría = new ArrayList<>();
        repartidorMaría.add(p1);
        repartidorMaría.add(p2);

        List<Pedido> repartidorFernando = new ArrayList<>();
        repartidorFernando.add(p3);

        //Instanciamos los repartidores a la clase repartidor y a la lista especifica de cada uno

        Repartidor r1 = new Repartidor("María Elgueta", repartidorMaría);
        Repartidor r2 = new Repartidor("Fernando Diaz", repartidorFernando);


        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(r1);
        executor.execute(r2);

        executor.shutdown();

        try {
            if (executor.awaitTermination(100, TimeUnit.SECONDS)){
                System.out.println("================== HISTORIAL DE PEDIDOS  ==================");
                System.out.println("Todos los repartidores han finalizado correctamente.");
                controlador.verHistorial();
            }else {
                System.out.println("El tiempo de espera se agotó antes de terminar.");
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }



    }
}