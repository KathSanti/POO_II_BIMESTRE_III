package modelo;

import controladores.Estado;
import controladores.ZonadeCarga;

public class Repartidor implements Runnable {

    private final String nombre;
    private final ZonadeCarga zonadeCarga;


    public Repartidor(String nombre, ZonadeCarga zonadeCarga) {
        this.nombre = nombre;
        this.zonadeCarga = zonadeCarga;
    }

    @Override
    public void run() {
        while (true) {
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
                System.out.println(nombre + " entregó: " + pedido);
            }else {
                break;
            }
        }

    }
}
