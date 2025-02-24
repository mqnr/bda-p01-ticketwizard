package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.dtos.AgregarSaldoDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AgregarFondosPanel extends JPanel {

    private final ControlUsuarios controlUsuarios;
    private final Usuario usuario;
    private final JTextField montoField;
    private final JLabel saldoActualLabel;
    private final NumberFormat formatoMoneda;

    public AgregarFondosPanel(ControlUsuarios controlUsuarios, Usuario usuario) {
        this.usuario = usuario;
        this.controlUsuarios = controlUsuarios;
        this.formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelNorte = new JPanel();
        panelNorte.add(new JLabel("Agregar fondos a tu cuenta"));
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(new JLabel("Saldo actual:"), gbc);

        gbc.gridx = 1;
        saldoActualLabel = new JLabel(formatoMoneda.format(usuario.getSaldo()));
        panelCentral.add(saldoActualLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(new JLabel("Monto a agregar:"), gbc);

        gbc.gridx = 1;
        montoField = new JTextField(10);
        panelCentral.add(montoField, gbc);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        JButton agregarButton = new JButton("Agregar fondos");
        agregarButton.addActionListener(e -> agregarFondos());
        panelSur.add(agregarButton);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void agregarFondos() {
        try {
            // parsear el monto ingresado
            String montoText = montoField.getText().replace("$", "").replace(",", "");
            BigDecimal monto = new BigDecimal(montoText);

            AgregarSaldoDTO dto = new AgregarSaldoDTO(usuario.getId(), monto);

            try {
                controlUsuarios.agregarSaldo(dto);

                // actualizar la vista con el nuevo saldo
                Usuario usuarioActualizado = controlUsuarios.obtenerUsuario(new ObtenerUsuarioIdDTO(usuario.getId()));
                saldoActualLabel.setText(formatoMoneda.format(usuarioActualizado.getSaldo()));

                montoField.setText("");

                DialogosUtil.mostrarExito(this, "Fondos agregados exitosamente");
            } catch (IllegalArgumentException e) {
                DialogosUtil.mostrarError(this, e.getMessage());
            }

        } catch (NumberFormatException e) {
            DialogosUtil.mostrarError(this, "Debes ingresar un monto válido");
        }
    }
}
