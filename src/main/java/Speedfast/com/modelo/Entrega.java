package Speedfast.com.modelo;

import java.sql.Time;
import java.sql.Date;

public class Entrega {

    private int idEntrega; // Nuevo campo para la PK de la tabla
    private int idPedido;
    private int idRepartidor;
    private Date fecha;
    private Time hora;


    public Entrega(int idEntrega, int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.idEntrega = idEntrega;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }


    public Entrega(int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdEntrega() { return idEntrega; }
    public void setIdEntrega(int idEntrega) { this.idEntrega = idEntrega; }

    public int getIdPedido() { return idPedido; }
    public int getIdRepartidor() { return idRepartidor; }
    public Date getFecha() { return fecha; }
    public Time getHora() { return hora; }
}