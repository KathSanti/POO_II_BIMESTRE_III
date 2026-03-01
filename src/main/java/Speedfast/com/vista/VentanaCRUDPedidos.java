package Speedfast.com.vista;

import Speedfast.com.controladores.ControladorPedidos;
import Speedfast.com.controladores.Estado;
import Speedfast.com.controladores.TipoPedido;
import Speedfast.com.dao.PedidoDAOImpl;
import Speedfast.com.interfacesDAO.PedidoDAO;
import Speedfast.com.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaCRUDPedidos extends JFrame {

    private ControladorPedidos controlador;
    private PedidoDAO pedidoDAO = new PedidoDAOImpl(); // Instanciamos el DAO para Editar/Eliminar

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private int idSeleccionado = -1; // Para guardar el ID del pedido a editar o eliminar

    // Componentes Registro/Edición
    private JTextField txtDireccion;
    private JComboBox<TipoPedido> cmbTipoRegistro;
    private JComboBox<Estado> cmbEstadoRegistro; // Agregado para poder editar el estado
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnLimpiar;

    // Componentes Filtro
    private JComboBox<String> cmbFiltroEstado;
    private JComboBox<String> cmbFiltroTipo;
    private JTextField txtFiltroRepartidor;

    public VentanaCRUDPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("CRUD Maestro de Pedidos - SpeedFast");
        setSize(950, 600);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // --- PANEL NORTE: REGISTRO Y FILTROS ---
        JPanel panelNorte = new JPanel(new GridLayout(2, 1));

        // 1. Sub-panel Registro y Edición
        JPanel panelRegistro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Datos del Pedido (Nuevo / Editar)"));

        panelRegistro.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(15);
        panelRegistro.add(txtDireccion);

        panelRegistro.add(new JLabel("Tipo:"));
        cmbTipoRegistro = new JComboBox<>(TipoPedido.values());
        panelRegistro.add(cmbTipoRegistro);

        panelRegistro.add(new JLabel("Estado:"));
        cmbEstadoRegistro = new JComboBox<>(Estado.values());
        cmbEstadoRegistro.setEnabled(false); // Por defecto deshabilitado al registrar uno nuevo
        panelRegistro.add(cmbEstadoRegistro);

        btnGuardar = new JButton("Registrar Nuevo");
        btnEditar = new JButton("Guardar Edición");
        btnLimpiar = new JButton("Limpiar");
        btnEditar.setEnabled(false); // Se habilita solo al seleccionar de la tabla

        panelRegistro.add(btnGuardar);
        panelRegistro.add(btnEditar);
        panelRegistro.add(btnLimpiar);

        // 2. Sub-panel Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        panelFiltros.add(new JLabel("Tipo:"));
        cmbFiltroTipo = new JComboBox<>(new String[]{"TODOS", "EXPRESS", "COMIDA", "ENCOMIENDA"});
        panelFiltros.add(cmbFiltroTipo);

        panelFiltros.add(new JLabel("Estado:"));
        cmbFiltroEstado = new JComboBox<>(new String[]{"TODOS", "PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        panelFiltros.add(cmbFiltroEstado);

        panelFiltros.add(new JLabel("Repartidor:"));
        txtFiltroRepartidor = new JTextField(10);
        panelFiltros.add(txtFiltroRepartidor);

        JButton btnFiltrar = new JButton("Aplicar Filtros");
        panelFiltros.add(btnFiltrar);

        panelNorte.add(panelRegistro);
        panelNorte.add(panelFiltros);
        add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA ---
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa en la celda
            }
        };
        tablaPedidos = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        tablaPedidos.setRowSorter(sorter);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // PANEL SUR: BOTÓN ELIMINAR
        JPanel panelSur = new JPanel();
        JButton btnEliminar = new JButton("Eliminar Pedido Seleccionado");
        panelSur.add(btnEliminar);
        add(panelSur, BorderLayout.SOUTH);

        // EVENTOS

        // SELECCIONAR FILA DE LA TABLA (Para Editar o Eliminar)
        tablaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaVista = tablaPedidos.getSelectedRow();
                if (filaVista != -1) {
                    // Convertir el índice de la vista al modelo (importante por los filtros)
                    int filaModelo = tablaPedidos.convertRowIndexToModel(filaVista);

                    idSeleccionado = (int) modeloTabla.getValueAt(filaModelo, 0);
                    txtDireccion.setText((String) modeloTabla.getValueAt(filaModelo, 1));
                    cmbTipoRegistro.setSelectedItem(modeloTabla.getValueAt(filaModelo, 2));
                    cmbEstadoRegistro.setSelectedItem(modeloTabla.getValueAt(filaModelo, 3));

                    // Cambiamos el modo de la interfaz a "Edición"
                    cmbEstadoRegistro.setEnabled(true);
                    btnGuardar.setEnabled(false);
                    btnEditar.setEnabled(true);
                }
            }
        });

        // BOTÓN LIMPIAR
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // 3. REGISTRAR (CREATE)
        btnGuardar.addActionListener(e -> {
            if(txtDireccion.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Ingrese la dirección del pedido.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.registrarPedido(txtDireccion.getText().trim(), (TipoPedido) cmbTipoRegistro.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
            limpiarFormulario();
            cargarDatos();
        });

        // ACTUALIZAR (UPDATE)
        btnEditar.addActionListener(e -> {
            if(idSeleccionado == -1) return;

            if(txtDireccion.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "La dirección no puede estar vacía.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Creamos un objeto Pedido con los datos actualizados
                Pedido pedidoActualizado = new Pedido(
                        idSeleccionado,
                        txtDireccion.getText().trim(),
                        (TipoPedido) cmbTipoRegistro.getSelectedItem(),
                        (Estado) cmbEstadoRegistro.getSelectedItem()
                );

                pedidoDAO.update(pedidoActualizado);
                JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente.");
                limpiarFormulario();
                cargarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR (DELETE)
        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar el pedido #" + idSeleccionado + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    pedidoDAO.delete(idSeleccionado); // Aquí intenta eliminar
                    JOptionPane.showMessageDialog(this, "Pedido eliminado exitosamente.");

                    tablaPedidos.clearSelection();
                    limpiarFormulario();
                    cargarDatos();
                } catch (RuntimeException ex) {

                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Acción Denegada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // FILTROS
        btnFiltrar.addActionListener(e -> aplicarFiltros());
    }

    private void limpiarFormulario() {
        txtDireccion.setText("");
        cmbTipoRegistro.setSelectedIndex(0);
        cmbEstadoRegistro.setSelectedItem(Estado.PENDIENTE);
        cmbEstadoRegistro.setEnabled(false);
        idSeleccionado = -1;
        tablaPedidos.clearSelection();

        // Restaurar botones
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = controlador.obtenerPedidos();
        for (Pedido p : lista) {
            Object[] fila = {
                    p.getIdPedido(),
                    p.getDireccionEntrega(),
                    p.getTipo(),
                    p.getEstado(),
                    p.getNombreRepartidor(),
            };
            modeloTabla.addRow(fila);
        }
    }

    private void aplicarFiltros() {
        List<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (!cmbFiltroTipo.getSelectedItem().equals("TODOS")) {
            filtros.add(RowFilter.regexFilter(cmbFiltroTipo.getSelectedItem().toString(), 2));
        }
        if (!cmbFiltroEstado.getSelectedItem().equals("TODOS")) {
            filtros.add(RowFilter.regexFilter(cmbFiltroEstado.getSelectedItem().toString(), 3));
        }
        if (!txtFiltroRepartidor.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + txtFiltroRepartidor.getText().trim(), 4));
        }

        sorter.setRowFilter(RowFilter.andFilter(filtros));
    }
}