package com.speedfast.sistema.controlador;

import com.speedfast.sistema.capability.Rastreable;
import com.speedfast.sistema.model.Pedido;

import java.util.List;

public class ControladorEnvios implements Rastreable {

    private List<Pedido> historialPedidos;

    public ControladorEnvios(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    public void recibirPedido(Pedido p) {
        historialPedidos.add(p);
        System.out.println("=== Nuevo Pedido Recibido ===");
        p.mostrarResumen();
    }

    @Override
    public void verHistorial() {
        System.out.println("\n======= HISTORIAL DE ENTREGAS =======");
        for (Pedido p : historialPedidos) {
            p.mostrarResumen();
        }
        System.out.println("=============================\n");
    }






}
