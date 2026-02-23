package Speedfast.com.vista;

import Speedfast.com.controladores.ControladorPedidos;
import Speedfast.com.controladores.TipoPedido;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    // 1. Declaramos los componentes como atributos de la clase
    private ControladorPedidos controlador;
    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public VentanaRegistroPedido(ControladorPedidos controlador) {
        this.controlador = controlador; // Recibimos el controlador
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registrar Nuevo Pedido");
        setSize(400, 300);
        setLocationRelativeTo(null);
        // Usamos GridLayout: 4 filas, 2 columnas, y espacio entre ellas
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // Fila 2: Dirección
        add(new JLabel("  Dirección de Entrega:"));
        txtDireccion = new JTextField();
        add(txtDireccion);

        //Fila 3: Tipo (ComboBox)
        add(new JLabel("  Tipo de Paquete:"));
        String[] opciones = {"Comida", "Encomienda", "Express"};
        cmbTipo = new JComboBox<>(opciones);
        add(cmbTipo);

        // Fila 4: Botones
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose()); // Cierra solo esta ventana
        add(btnCancelar);

        btnGuardar = new JButton("Guardar");
        // Lógica del botón Guardar
        btnGuardar.addActionListener(e -> guardarPedido());
        add(btnGuardar);
    }

    private void guardarPedido() {
        if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String direccion = txtDireccion.getText();
        String tipo = (String) cmbTipo.getSelectedItem();
        TipoPedido tipoEnum = TipoPedido.valueOf(tipo.toUpperCase());

        controlador.registrarPedido(direccion, tipoEnum); // Ya no pasamos el ID
        JOptionPane.showMessageDialog(this, "¡Pedido registrado con éxito!");
        txtDireccion.setText("");
    }
}