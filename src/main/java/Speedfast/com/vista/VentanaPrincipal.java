package Speedfast.com.vista;

import Speedfast.com.controladores.ControladorPedidos;
import javax.swing.*;
import java.awt.*;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new GridLayout(4, 1, 5, 5));


        JButton btnListar = new JButton("Gestión de pedidos");
        JButton btnIniciar = new JButton("Gestión Entregas Automáticas");
        JButton btnGestionEntregas = new JButton("Gestión Entregas (CRUD)");
        JButton btnGestionRepartidores = new JButton("Gestión Repartidores (CRUD)");


        add(btnListar);
        add(btnIniciar);
        add(btnGestionEntregas);
        add(btnGestionRepartidores);



        btnListar.addActionListener(e -> {
            new VentanaCRUDPedidos(controladorP).setVisible(true);
        });

        btnIniciar.addActionListener(e -> {
            controladorP.iniciarEntregas();
            JOptionPane.showMessageDialog(this, "¡Motoristas saliendo! Revise la consola.");
        });

        btnGestionEntregas.addActionListener(e -> {
            new VentanaGestionEntregas().setVisible(true);
        });

        btnGestionRepartidores.addActionListener(e -> {
            new VentanaGestionRepartidores().setVisible(true);
        });
    }
}