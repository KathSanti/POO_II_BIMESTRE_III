package modelo;

import controladores.Estado;
import controladores.TipoPedido;

public class Pedido implements Comparable<Pedido> {

    private final  int idPedido;
    private final String direccionEntrega;
    private TipoPedido tipo;
    private Estado estado;
    private String nombreRepartidor = "Sin asignar";


    public Pedido(int idPedido, String direccionEntrega, TipoPedido tipo, Estado estado) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getIdPedido() {return idPedido;}

    public TipoPedido getTipo() {
        return tipo;
    }

    public void setTipo(TipoPedido tipo) {
        this.tipo = tipo;
    }

    public String getDireccionEntrega() {return direccionEntrega;}

    public Estado getEstado() {return estado;}

    public void setEstado(Estado nuevoEstado) {this.estado = nuevoEstado;}

    public String getNombreRepartidor() { return nombreRepartidor; }

    public void setNombreRepartidor(String nombreRepartidor) {this.nombreRepartidor = nombreRepartidor;}


    @Override
    public int compareTo(Pedido otro) {
        int comparacionTipo = this.tipo.compareTo(otro.tipo);

        if (comparacionTipo != 0) {
            return comparacionTipo;
        }

        return Integer.compare(this.idPedido, otro.idPedido);
    }

    @Override
    public String toString() {
        return "[ID: " + idPedido + " | Tipo: " + tipo + " | Estado: " + estado + "]";
    }

}
