package com.duoc.speedfast;


import com.duoc.speedfast.pedido.ComidaPedido;
import com.duoc.speedfast.pedido.EncomiendaPedido;
import com.duoc.speedfast.pedido.ExpressPedido;
import com.duoc.speedfast.pedido.Pedido;

public class Main {
    public static void main(String[] args) {


        Pedido comida = new ComidaPedido("001","Playa las docas, 0181, valparaiso", 9.2);
        Pedido encomienda = new EncomiendaPedido("002", "Jose miguel, 2740, Macul", 5);
        Pedido express = new ExpressPedido("003", "Villa nueva 4785, Renca", 6);

        Pedido [] proceso ={
                comida,
                encomienda,
                express
        };

        for (Pedido pedido : proceso) {
            pedido.ejecutarPedido();
        }


    }
}