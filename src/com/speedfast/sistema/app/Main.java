package com.speedfast.sistema.app;


import com.speedfast.sistema.controlador.ControladorEnvios;
import com.speedfast.sistema.model.ComidaPedido;
import com.speedfast.sistema.model.EncomiendaPedido;
import com.speedfast.sistema.model.ExpressPedido;
import com.speedfast.sistema.model.Pedido;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        ControladorEnvios controlador = new ControladorEnvios(new ArrayList<>());

        Pedido p1 = new ComidaPedido("001", "Viña del mar 557, valparaiso",7,"María Elgueta");
        Pedido p2= new ExpressPedido("002", "Las Margaritas, 2470, Macul", 4, "Fernando Diaz");
        Pedido p3= new EncomiendaPedido("003","Zennit 1470, Maitenes", 19,"Andrea Jimenez");


        controlador.recibirPedido(p1);
        p1.asignarRepartidor();
        controlador.recibirPedido(p2);
        p2.asignarRepartidor();
        controlador.recibirPedido(p3);
        p3.asignarRepartidor();


        System.out.println("\n==== Detalle despacho ====");
        p1.despachar();

        System.out.println("\n==== Cancelar Pedido ====");

        controlador.cancelar(p2.getIdPedido());


        // Prueba Ver Historial (Interfaz Rastreable implementada en Controlador)
        controlador.verHistorial();





    }
}