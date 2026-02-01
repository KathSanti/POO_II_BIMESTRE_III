package com.speedFast.concurrente.controlador;

import com.speedFast.concurrente.capability.Cancelable;
import com.speedFast.concurrente.capability.Rastreable;
import com.speedFast.concurrente.model.Pedido;

import java.util.List;

public class ControladorEnvios implements Rastreable, Cancelable {

    private List<Pedido> historialPedidos;

    public ControladorEnvios(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    public void recibirPedido(Pedido pedido) {
        historialPedidos.add(pedido);
        System.out.println("=== Nuevo Pedido: [ID: " + pedido.getIdPedido() + "] -> Despacho: " + pedido.getDireccionEntrega() + " ===");
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
