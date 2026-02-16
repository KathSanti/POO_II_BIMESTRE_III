package vista;

import controladores.ControladorPedidos;
import controladores.TipoPedido;

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
        // Fila 1: ID
        add(new JLabel("  ID del Pedido:")); // Etiqueta
        txtId = new JTextField();           // Campo de texto
        add(txtId);

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
        // 1. Validaciones básicas
        if (txtId.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // 2. Capturar datos
            int id = Integer.parseInt(txtId.getText()); // Puede fallar si no es número
            String direccion = txtDireccion.getText();
            String tipo = (String) cmbTipo.getSelectedItem();
            TipoPedido tipoEnum = TipoPedido.valueOf(tipo.toUpperCase());

            // 3. LLAMAR AL CONTROLADOR
            controlador.registrarPedido(id, direccion, tipoEnum);

            // 4. Mensaje al usuario
            JOptionPane.showMessageDialog(this, "¡Pedido registrado con éxito!");

            // 5. Limpiar campos o cerrar ventana
            txtId.setText("");
            txtDireccion.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}