package Speedfast.com.modelo;

import Speedfast.com.controladores.Estado;
import Speedfast.com.controladores.ZonadeCarga;
import Speedfast.com.dao.EntregaDAO;
import Speedfast.com.dao.PedidoDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Repartidor implements Runnable {

    private final int idRepartidor;
    private final String nombre;
    private final ZonadeCarga zonadeCarga;
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();

    public Repartidor(int idRepartidor, String nombre, ZonadeCarga zonadeCarga) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.zonadeCarga = zonadeCarga;

    }

    public int getIdRepartidor() {return idRepartidor;}

    public String getNombre() {return nombre;}

    @Override
    public void run() {
        while (true) {

            // --- MENSAJE ESPÍA ---
            System.out.println(">>> HILO INICIADO: Motorista " + nombre + " listo y esperando pedidos...");

            Pedido pedido = zonadeCarga.retirarPedido();


            if (pedido != null) {
                try{
                    pedido.setNombreRepartidor(this.nombre);
                    System.out.println(nombre + " Retirando pedido #" + pedido.getIdPedido() + "...");
                    pedido.setEstado(Estado.EN_REPARTO);
                    System.out.println("[PEDIDO EN REPARTO] " + pedido.getIdPedido() + " dirigiendose a : " + pedido.getDireccionEntrega());
                    Thread.sleep(4000);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }

                pedido.setEstado(Estado.ENTREGADO);
                pedidoDAO.actualizarEstado(pedido.getIdPedido(), Estado.ENTREGADO);

                Entrega entrega = new Entrega(
                        pedido.getIdPedido(),
                        this.idRepartidor,
                        Date.valueOf(LocalDate.now()),
                        Time.valueOf(LocalTime.now())
                );

                entregaDAO.guardar(entrega);

                System.out.println(nombre + " entregó: " + pedido);
            }else {
                break;
            }
        }

    }
}
