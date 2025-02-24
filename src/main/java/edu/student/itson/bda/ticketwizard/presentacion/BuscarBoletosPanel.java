package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlBoletos;
import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.dtos.BoletoDisponibleDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class BuscarBoletosPanel extends PanelBaseUsuario {

    private final ControlBoletos controlBoletos;
    private JTable tablaBoletos;
    private DefaultTableModel modeloTabla;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<BoletoDisponibleDTO> boletosActuales;

    public BuscarBoletosPanel(ControlUsuarios controlUsuarios, ControlBoletos controlBoletos, Usuario usuario) {
        super(controlUsuarios, usuario);
        this.controlBoletos = controlBoletos;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Boletos Disponibles");
        titulo.setFont(new Font("Dialog", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        String[] columnas = {
            "Evento", "Fecha", "Ubicación", "Asiento", "Precio", "Vendedor", "Tipo", "Acciones"
        };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Solo la columna de acciones
            }
        };

        tablaBoletos = new JTable(modeloTabla);
        tablaBoletos.setFillsViewportHeight(true);
        tablaBoletos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaBoletos.getColumnModel().getColumn(0).setPreferredWidth(200); // Evento
        tablaBoletos.getColumnModel().getColumn(1).setPreferredWidth(100); // Fecha
        tablaBoletos.getColumnModel().getColumn(2).setPreferredWidth(200); // Ubicación
        tablaBoletos.getColumnModel().getColumn(3).setPreferredWidth(100); // Asiento
        tablaBoletos.getColumnModel().getColumn(4).setPreferredWidth(100); // Precio
        tablaBoletos.getColumnModel().getColumn(5).setPreferredWidth(150); // Vendedor
        tablaBoletos.getColumnModel().getColumn(6).setPreferredWidth(100); // Tipo
        tablaBoletos.getColumnModel().getColumn(7).setPreferredWidth(100); // Acciones

        tablaBoletos.getColumn("Acciones").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton button = new JButton("Comprar");
            button.setOpaque(true);
            return button;
        });

        tablaBoletos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaBoletos.rowAtPoint(evt.getPoint());
                int columna = tablaBoletos.columnAtPoint(evt.getPoint());
                if (columna == 7 && fila >= 0) {
                    comprarBoleto(fila);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaBoletos);
        add(scrollPane, BorderLayout.CENTER);

        refrescarDatos();
    }

    @Override
    public void refrescarDatos() {
        boletosActuales = controlBoletos.consultarBoletosDisponibles();
        modeloTabla.setRowCount(0);

        for (BoletoDisponibleDTO b : boletosActuales) {
            Object[] fila = {
                b.getNombreEvento(),
                b.getFechaEvento().format(formatoFecha),
                b.getUbicacionCompleta(),
                b.getAsientoCompleto(),
                String.format("$%.2f", b.getPrecioVenta()),
                b.getNombreVendedor(),
                b.esVentaInicial() ? "Venta Inicial" : "Reventa",
                "Comprar"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void comprarBoleto(int fila) {
        if (fila < 0 || fila >= boletosActuales.size()) {
            return;
        }

        BoletoDisponibleDTO boleto = boletosActuales.get(fila);

        try {
            if (!DialogosUtil.confirmar(this,
                    String.format("¿Desea comprar este boleto por $%.2f?%n%s - %s",
                            boleto.getPrecioVenta(),
                            boleto.getNombreEvento(),
                            boleto.getAsientoCompleto()))) {
                return;
            }

            controlBoletos.comprarBoleto(boleto.getNumeroSerie(), usuario.getId());
            DialogosUtil.mostrarExito(this, "¡Boleto comprado exitosamente!");
            refrescarDatos();

        } catch (IllegalStateException e) {
            DialogosUtil.mostrarAdvertencia(this, e.getMessage());
        } catch (Exception e) {
            DialogosUtil.mostrarError(this, "Error al procesar la compra: " + e.getMessage());
        }
    }
}
