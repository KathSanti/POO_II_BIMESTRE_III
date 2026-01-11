package com.duoc.speedFast;

public abstract class Pedido {

    protected String idPedido;
    protected String direccionEntrega;
    protected String tipoPedido;

    public Pedido(String idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }



   //Metodo sobrecargado para impmentar en subclases

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor " + nombreRepartidor + " para pedido: " + idPedido);
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
}