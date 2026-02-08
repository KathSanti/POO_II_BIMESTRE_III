package speedFast.Concurrente.com;

import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {

        // Cola compartida
        PriorityBlockingQueue<Pedido> cola = new PriorityBlockingQueue<>();
        ZonadeCarga zonaCarga = new ZonadeCarga(cola);

        zonaCarga.agregarPedido(new Pedido(1, "Av. Eliseo 123, Macul", Estado.PENDIENTE));
        zonaCarga.agregarPedido(new Pedido(2, "Calle Manhatan 456, Quilicura", Estado.PENDIENTE));
        zonaCarga.agregarPedido(new Pedido(3, "Pasaje Bella Vista 789, Pudahuel", Estado.PENDIENTE));
        zonaCarga.agregarPedido(new Pedido(4, "Av. Cerro alegre 321, San Miguel", Estado.PENDIENTE));
        zonaCarga.agregarPedido(new Pedido(5, "Av. Matta  740, San Felipe", Estado.PENDIENTE));


        Thread repartidor1 = new Thread(new Repartidor("María Carrasco",zonaCarga));
        Thread repartidor2 = new Thread(new Repartidor("Carlos Dominguez",zonaCarga));
        Thread repartidor3 = new Thread(new Repartidor("Elisa Juarez",zonaCarga));

        repartidor1.start();
        repartidor2.start();
        repartidor3.start();


        try {
            repartidor1.join();
            repartidor2.join();
            repartidor3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // CUMPLIMIENTO REQUISITO: Mensaje final
        System.out.println("Todos los pedidos han sido entregados correctamente.");
    }
}