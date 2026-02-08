package speedFast.Concurrente.com;

public class Pedido implements Comparable<Pedido> {

    private final  int idPedido;
    private final String direccionEntrega;
    private Estado estado;

    public Pedido(int idPedido, String direccionEntrega, Estado estado) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
    }

    public int getIdPedido() {return idPedido;}

    public String getDireccionEntrega() {return direccionEntrega;}

    public Estado getEstado() {return estado;}

    public void setEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;

    }


    @Override
    public int compareTo(Pedido otro) {
        return this.estado.compareTo(otro.estado);
    }

    @Override
    public String toString() {
        return "[Pedido : " +
                 idPedido +
                ", Direccion entrega : " + direccionEntrega  +
                ']';
    }

}
