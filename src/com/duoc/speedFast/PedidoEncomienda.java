package com.duoc.speedFast;

public class PedidoEncomienda extends Pedido {

    private double peso;
    private boolean embalaje;

    public PedidoEncomienda(String idPedido, String direccionEntrega, String tipoPedido, double peso, boolean embalaje ) {
        super(idPedido, direccionEntrega, tipoPedido);
        this.peso = peso;
        this.embalaje = embalaje;

    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("\n========== Asignación para Encomienda ===========");
        System.out.println("Pedido        : " + idPedido);
        System.out.println("Articulo      :" + tipoPedido) ;
        System.out.println("Dirección     : " + direccionEntrega);
        System.out.println("Validando peso del paquete: " + peso + " kg");
        System.out.println("Repartidor asignado: "  + nombreRepartidor);

        if (peso> 10) {
            System.out.println("ADVERTENCIA: Paquete pesado, requiere repartidor especializado");
        }

        if (embalaje == true) {
            System.out.println("Verificando embalaje especial...");
            System.out.println("Embalaje especial confirmado");
        }

        System.out.println("Repartidor asignado para encomienda");
        System.out.println("====================================\n");
    }

}
