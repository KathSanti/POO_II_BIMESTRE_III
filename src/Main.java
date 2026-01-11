import com.duoc.speedFast.PedidoComida;
import com.duoc.speedFast.PedidoEncomienda;
import com.duoc.speedFast.PedidoExpress;


public class Main {
    public static void main(String[] args) {

        System.out.println("====== SISTEMA DE REPARTO SPEEDFAST ======\n");

        PedidoComida pedidoComida = new PedidoComida("001", "Av. Monte Verde 145", "Helado",true);
        PedidoEncomienda pedidoEncomienda = new PedidoEncomienda("002", "Calle Secundaria 456","Ceramica", 8.5, true);
        PedidoExpress pedidoExpress = new PedidoExpress("003", "Av. Central 789","lentejas" , "Supermercado Central",true);

        pedidoComida.asignarRepartidor("Carlos Rodríguez");
        pedidoEncomienda.asignarRepartidor("María González");
        pedidoExpress.asignarRepartidor("Juan Pérez");



    }
}