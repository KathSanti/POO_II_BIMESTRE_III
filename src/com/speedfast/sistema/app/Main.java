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
        controlador.recibirPedido(p2);
        controlador.recibirPedido(p3);


        System.out.println(" ==== Detalle despacho ====");
        p1.despachar();

        System.out.println(" ==== Cancelar Pedido ====");
        p2.cancelar();

        // Prueba Ver Historial (Interfaz Rastreable implementada en Controlador)
        controlador.verHistorial();















    }
}