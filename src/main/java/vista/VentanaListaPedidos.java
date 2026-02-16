package vista;

import controladores.ControladorPedidos;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private ControladorPedidos controlador;

    // Recibimos el controlador para poder pedirle la lista de pedidos
    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Listado de Pedidos - SpeedFast");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centrar en pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cerrar esta ventana, no la app
    }

    private void inicializarComponentes() {
        // 1. Configurar el Modelo de la Tabla (Las columnas)
        // Definimos los nombres de las columnas que se verán arriba
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};
        modeloTabla = new DefaultTableModel(columnas, 0); // 0 indica que empieza sin filas

        // 2. Crear la Tabla y asignarle el modelo
        tablaPedidos = new JTable(modeloTabla);

        // Tabla dentro de un ScrollPane por si hay muchos pedidos
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Crear el Botón de Actualizar
        JButton btnActualizar = new JButton("Actualizar Listado");
        add(btnActualizar, BorderLayout.SOUTH);

        // 4. Darle vida al botón (ActionListener)
        btnActualizar.addActionListener(e -> {
            cargarPedidos(); // Llamamos al mEtodo que rellena la tabla
        });

        // Cargar los pedidos automáticamente al abrir la ventana
        cargarPedidos();
    }

    // Metodo  que conecta la Vista con el Controlador
    private void cargarPedidos() {
        //Limpiar la tabla para no repetir datos
        modeloTabla.setRowCount(0);

        // Pedimos la lista al controlador
        List<Pedido> lista = controlador.obtenerPedidos();


        // Recorremos la lista y agregamos cada pedido como una fila
        for (Pedido p : lista) {
            Object[] fila = {
                    p.getIdPedido(),
                    p.getDireccionEntrega(),
                    p.getTipo(),    // Asegúrate de haber agregado este getter en Pedido
                    p.getEstado(),
                    p.getNombreRepartidor()

            };
            modeloTabla.addRow(fila); // Agrega la fila al modelo visual
        }
    }
}