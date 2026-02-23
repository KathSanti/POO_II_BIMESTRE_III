package Speedfast.com.vista;

import Speedfast.com.controladores.ControladorPedidos;
import javax.swing.*;
import java.awt.*; // Necesario para los Layouts

public class VentanaPrincipal extends JFrame {

    private ControladorPedidos controladorP;

    public VentanaPrincipal() {
        controladorP = new ControladorPedidos();
        configurarInterfaz();
    }

    public void configurarInterfaz(){
        setTitle("Sistema SpeedFast - Menú Principal");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra toda la app

        // Usamos GridLayout para que los botones vayan uno bajo  del otro
        setLayout(new GridLayout(3, 1, 10, 20));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnIniciar = new JButton("Iniciar Entregas");

        add(btnRegistrar);
        add(btnListar);
        add(btnIniciar);
        // ----------------------------------------------------------------------

        // Acción: Abrir ventana de registro
        btnRegistrar.addActionListener(e -> {
            new VentanaRegistroPedido(controladorP).setVisible(true);
        });

        // Acción: Abrir ventana de lista
        btnListar.addActionListener(e -> {
            new VentanaListaPedidos(controladorP).setVisible(true);
        });

        // Acción: Iniciar los hilos de repartidores
        btnIniciar.addActionListener(e -> {
            controladorP.iniciarEntregas();
            JOptionPane.showMessageDialog(this, "¡Motoristas saliendo! Revise la consola.");
        });
    }
}