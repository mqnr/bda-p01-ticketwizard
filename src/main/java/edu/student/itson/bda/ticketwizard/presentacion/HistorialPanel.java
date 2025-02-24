package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.control.ControlTransacciones;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.dtos.TransaccionDetalleDTO;
import edu.student.itson.bda.ticketwizard.entidades.Transaccion;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class HistorialPanel extends PanelBaseUsuario {

    private final ControlTransacciones controlTransacciones;
    private JTable tablaTransacciones;
    private DefaultTableModel modeloTabla;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public HistorialPanel(ControlUsuarios controlUsuarios, ControlTransacciones transaccionesControl, Usuario usuario) {
        super(controlUsuarios, usuario);
        this.controlTransacciones = transaccionesControl;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Historial de Transacciones");
        titulo.setFont(new Font("Dialog", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        String[] columnas = {
            "Fecha", "Tipo", "Evento", "Asiento", "Rol", "Monto", "Comisión"
        };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTransacciones = new JTable(modeloTabla);
        tablaTransacciones.setFillsViewportHeight(true);
        tablaTransacciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaTransacciones.getColumnModel().getColumn(0).setPreferredWidth(150); // Fecha
        tablaTransacciones.getColumnModel().getColumn(1).setPreferredWidth(100); // Tipo
        tablaTransacciones.getColumnModel().getColumn(2).setPreferredWidth(250); // Evento
        tablaTransacciones.getColumnModel().getColumn(3).setPreferredWidth(150); // Asiento
        tablaTransacciones.getColumnModel().getColumn(4).setPreferredWidth(100); // Rol
        tablaTransacciones.getColumnModel().getColumn(5).setPreferredWidth(100); // Monto
        tablaTransacciones.getColumnModel().getColumn(6).setPreferredWidth(100); // Comisión

        JScrollPane scrollPane = new JScrollPane(tablaTransacciones);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> filtroTipo = new JComboBox<>(new String[]{"Todos", "Compra Inicial", "Reventa"});
        JComboBox<String> filtroRol = new JComboBox<>(new String[]{"Todos", "Comprador", "Vendedor"});

        panelFiltros.add(new JLabel("Tipo:"));
        panelFiltros.add(filtroTipo);
        panelFiltros.add(Box.createHorizontalStrut(20));
        panelFiltros.add(new JLabel("Rol:"));
        panelFiltros.add(filtroRol);

        filtroTipo.addActionListener(e -> refrescarDatos());
        filtroRol.addActionListener(e -> refrescarDatos());

        add(panelFiltros, BorderLayout.SOUTH);

        refrescarDatos();
    }

    @Override
    public void refrescarDatos() {
        List<TransaccionDetalleDTO> transacciones = controlTransacciones.consultarTransaccionesUsuario(
                new ObtenerUsuarioIdDTO(usuario.getId())
        );

        modeloTabla.setRowCount(0);

        for (TransaccionDetalleDTO t : transacciones) {
            String rol = determinarRol(t);
            Object[] fila = {
                t.getFechaTransaccion().format(formatoFecha),
                t.getTipoAdquisicion() == Transaccion.TipoAdquisicion.VENTA_INICIAL
                ? "Compra Inicial" : "Reventa",
                t.getEventoCompleto(),
                t.getAsientoCompleto(),
                rol,
                String.format("$%.2f", t.getPrecio()),
                t.getComision() != null ? String.format("$%.2f", t.getComision()) : "-"
            };
            modeloTabla.addRow(fila);
        }
    }

    private String determinarRol(TransaccionDetalleDTO t) {
        if (t.getTipoAdquisicion() == Transaccion.TipoAdquisicion.VENTA_INICIAL) {
            return "Comprador";
        }

        if (t.getCompradorId() == usuario.getId()) {
            return "Comprador";
        } else if (t.getVendedorId() == usuario.getId()) {
            return "Vendedor";
        }

        return "Desconocido"; // esto no debería ocurrir
    }
}
