package Speedfast.com.modelo;

import Speedfast.com.controladores.Estado;
import Speedfast.com.controladores.ZonadeCarga;
import Speedfast.com.dao.EntregaDAOImpl;
import Speedfast.com.dao.PedidoDAOImpl;
import Speedfast.com.interfacesDAO.EntregaDAO;
import Speedfast.com.interfacesDAO.PedidoDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Repartidor implements Runnable {

    private final int idRepartidor;
    private final String nombre;
    private ZonadeCarga zonadeCarga;
    private PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private EntregaDAO entregaDAO = new EntregaDAOImpl();

    public Repartidor(int idRepartidor, String nombre, ZonadeCarga zonadeCarga) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.zonadeCarga = zonadeCarga;
    }

    public int getIdRepartidor() { return idRepartidor; }

    public String getNombre() { return nombre; }


    public void setZonadeCarga(ZonadeCarga zonadeCarga) {
        this.zonadeCarga = zonadeCarga;
    }


    @Override
    public String toString() {
        return idRepartidor + " - " + nombre;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(">>> HILO INICIADO: Motorista " + nombre + " listo y esperando pedidos...");


            if (zonadeCarga == null) {
                System.err.println("Error: El repartidor " + nombre + " no tiene Zona de Carga asignada.");
                break;
            }

            Pedido pedido = zonadeCarga.retirarPedido();

            if (pedido != null) {
                try {
                    pedido.setNombreRepartidor(this.nombre);
                    System.out.println(nombre + " Retirando pedido #" + pedido.getIdPedido() + "...");
                    pedido.setEstado(Estado.EN_REPARTO);
                    System.out.println("[PEDIDO EN REPARTO] " + pedido.getIdPedido() + " dirigiendose a : " + pedido.getDireccionEntrega());


                    pedidoDAO.update(pedido);

                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                pedido.setEstado(Estado.ENTREGADO);


                pedidoDAO.update(pedido);

                Entrega entrega = new Entrega(
                        pedido.getIdPedido(),
                        this.idRepartidor,
                        Date.valueOf(LocalDate.now()),
                        Time.valueOf(LocalTime.now())
                );


                entregaDAO.create(entrega);

                System.out.println(nombre + " entregó: " + pedido);
            } else {
                break;
            }
        }
    }
}