package Speedfast.com.vista;

import Speedfast.com.dao.EntregaDAOImpl;
import Speedfast.com.dao.PedidoDAOImpl;
import Speedfast.com.dao.RepartidorDAOImpl;
import Speedfast.com.interfacesDAO.EntregaDAO;
import Speedfast.com.interfacesDAO.PedidoDAO;
import Speedfast.com.interfacesDAO.RepartidorDAO;
import Speedfast.com.modelo.Entrega;
import Speedfast.com.modelo.Pedido;
import Speedfast.com.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VentanaGestionEntregas extends JFrame {

    // DAOs
    private EntregaDAO entregaDAO = new EntregaDAOImpl();
    private PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private RepartidorDAO repartidorDAO = new RepartidorDAOImpl();

    // Componentes UI
    private JComboBox<Pedido> comboPedidos;
    private JComboBox<Repartidor> comboRepartidores;
    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    public VentanaGestionEntregas() {
        configurarVentana();
        inicializarComponentes();
        cargarDatosCombos();
        cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Gestión de Entregas - SpeedFast");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        //PANEL SUPERIOR: Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignar Nueva Entrega"));

        panelFormulario.add(new JLabel("Seleccione Pedido:"));
        comboPedidos = new JComboBox<>();
        panelFormulario.add(comboPedidos);

        panelFormulario.add(new JLabel("Seleccione Repartidor:"));
        comboRepartidores = new JComboBox<>();
        panelFormulario.add(comboRepartidores);

        JButton btnRegistrar = new JButton("Registrar Entrega Manual");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        //PANEL CENTRAL: Tabla
        String[] columnas = { "ID Entrega", "ID Pedido", "Nombre Repartidor", "Fecha", "Hora"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaEntregas = new JTable(modeloTabla);
        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        //PANEL INFERIOR: Botones de Acción
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar Seleccionada");
        JButton btnActualizar = new JButton("Actualizar Tabla");
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS (CRUD y Validaciones)

        // CREAR
        btnRegistrar.addActionListener(e -> {
            Pedido pedidoSel = (Pedido) comboPedidos.getSelectedItem();
            Repartidor repartidorSel = (Repartidor) comboRepartidores.getSelectedItem();

            // Validación de entradas
            if (pedidoSel == null || repartidorSel == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Conservamos el ID de los objetos seleccionados para mandarlos a la BD
                Entrega nuevaEntrega = new Entrega(
                        pedidoSel.getIdPedido(),
                        repartidorSel.getIdRepartidor(),
                        Date.valueOf(LocalDate.now()),
                        Time.valueOf(LocalTime.now())
                );

                entregaDAO.create(nuevaEntrega);
                JOptionPane.showMessageDialog(this, "Entrega registrada exitosamente.");
                cargarTabla(); // Refrescar la tabla

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar la entrega: " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaEntregas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idEntrega = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar la entrega #" + idEntrega + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    entregaDAO.delete(idEntrega);
                    JOptionPane.showMessageDialog(this, "Entrega eliminada.");
                    cargarTabla();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // REFRESCAR TABLA
        btnActualizar.addActionListener(e -> cargarTabla());
    }

    //MÉTODOS DE CARGA DE DATOS

    private void cargarDatosCombos() {
        try {
            // Cargar Pedidos en el Combo (Gracias al toString() de Pedido, se verá bien)
            List<Pedido> pedidos = pedidoDAO.readAll();
            for (Pedido p : pedidos) {
                comboPedidos.addItem(p);
            }

            // Cargar Repartidores en el Combo
            List<Repartidor> repartidores = repartidorDAO.readAll();
            for (Repartidor r : repartidores) {
                comboRepartidores.addItem(r);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar listas desplegables.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        try {
            List<Entrega> lista = entregaDAO.readAll();
            for (Entrega e : lista) {
                Object[] fila = {
                        e.getIdEntrega(), // Asegúrate de tener este getter en tu modelo Entrega
                        e.getIdPedido(),
                        e.getIdRepartidor(),
                        e.getFecha(),
                        e.getHora()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla de entregas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}