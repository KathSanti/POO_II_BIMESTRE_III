package com.duoc.speedFast;

public class PedidoComida extends Pedido {

    private boolean mochilaTermica;

    public PedidoComida(String idPedido, String direccionEntrega, String tipoPedido, boolean mochilaTermica) {
        super(idPedido, direccionEntrega, tipoPedido);
        this.mochilaTermica = mochilaTermica;
    }




    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("\n========== Asignción pedido comida ===========");
        System.out.println("Pedido comida       : " + idPedido);
        System.out.println("Articulo            :" + tipoPedido) ;
        System.out.println("Repartidor asignado : "  + nombreRepartidor);

        if (mochilaTermica == true) {
            System.out.println("Verificando equipamiento de " + nombreRepartidor + "......" );
            System.out.println("asignado al pedido de comida (mochila térmica confirmada)");
        } else {
            System.out.println("Este pedido no requiere mochila térmica");
        }
        System.out.println("====================================\n");
    }

}
