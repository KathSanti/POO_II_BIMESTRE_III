package com.speedfast.sistema.controlador;

import com.speedfast.sistema.capability.Cancelable;
import com.speedfast.sistema.capability.Rastreable;
import com.speedfast.sistema.model.Pedido;

import java.util.List;

public class ControladorEnvios implements Rastreable, Cancelable {

    private List<Pedido> historialPedidos;

    public ControladorEnvios(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    public void recibirPedido(Pedido pedido) {
        historialPedidos.add(pedido);
        System.out.println("=== Nuevo Pedido Recibido ===");
        pedido.mostrarResumen();
    }

    @Override
    public void verHistorial() {
        System.out.println("\n======= HISTORIAL DE ENTREGAS =======");
        for (Pedido pedido : historialPedidos) {
            pedido.mostrarResumen();
        }
        System.out.println("=============================\n");
    }



    @Override
    public void cancelar(String idPedido){
        historialPedidos.removeIf(pedido -> pedido.getIdPedido().equals(idPedido));
        System.out.println("El pedido #" + idPedido+ " ha sido cancelado.");

    }






}
