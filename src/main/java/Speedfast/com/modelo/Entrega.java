package Speedfast.com.modelo;

import java.sql.Time;
import java.sql.Date;

public class Entrega {

    private int idPedido;
    private int IdRepartidor;
    private Date fecha;
    private Time hora;


    public Entrega(int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.idPedido = idPedido;
        IdRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdPedido() {return idPedido;}

    public int getIdRepartidor() {return IdRepartidor;}

    public Date getFecha() {return fecha;}

    public Time getHora() {return hora;}
}
