package Speedfast.com.vista;

import Speedfast.com.dao.RepartidorDAOImpl;
import Speedfast.com.interfacesDAO.RepartidorDAO;
import Speedfast.com.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaGestionRepartidores extends JFrame {

    // Instanciamos el DAO usando la interfaz
    private RepartidorDAO repartidorDAO = new RepartidorDAOImpl();


    private JTextField txtNombre;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;
    private int idSeleccionado = -1; // Para saber qué repartidor estamos editando/eliminando

    public VentanaGestionRepartidores() {
        configurarVentana();
        inicializarComponentes();
        cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Gestión de Repartidores - SpeedFast");
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // --- PANEL SUPERIOR: Formulario ---
        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Repartidor"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre);

        JButton btnLimpiar = new JButton("Limpiar Selección");
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);

        //PANEL CENTRAL: Tabla
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que editen directamente en la celda
            }
        };
        tablaRepartidores = new JTable(modeloTabla);
        add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);

        // PANEL INFERIOR: Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Guardar Edición");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS DE INTERACCIÓN

        //  Seleccionar una fila de la tabla para editar o eliminar
        tablaRepartidores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaRepartidores.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                    txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
                }
            }
        });

        // Limpiar el formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // CREAR
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                repartidorDAO.create(nombre);
                JOptionPane.showMessageDialog(this, "Repartidor registrado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ACTUALIZAR
        btnEditar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nuevoNombre = txtNombre.getText().trim();
            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                repartidorDAO.update(idSeleccionado, nuevoNombre);
                JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar este repartidor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    repartidorDAO.delete(idSeleccionado);
                    JOptionPane.showMessageDialog(this, "Repartidor eliminado.");
                    limpiarFormulario();
                    cargarTabla();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // MÉTODOS AUXILIARES

    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpia las filas actuales
        try {
            List<Repartidor> lista = repartidorDAO.readAll(); // Lee la base de datos
            for (Repartidor r : lista) {
                Object[] fila = { r.getIdRepartidor(), r.getNombre() };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        idSeleccionado = -1; // Reseteamos la selección
        tablaRepartidores.clearSelection();
    }
}