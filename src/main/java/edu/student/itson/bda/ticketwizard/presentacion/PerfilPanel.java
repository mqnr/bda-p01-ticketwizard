package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PerfilPanel extends PanelBaseUsuario {

    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField direccionField;
    private JLabel fechaNacimientoLabel;
    private JLabel edadLabel;
    private JLabel saldoLabel;

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PerfilPanel(ControlUsuarios controlUsuarios, Usuario usuario) {
        super(controlUsuarios, usuario);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inicializarComponentes();
        cargarDatosUsuario();
    }

    @Override
    public void refrescarDatos() {
        Usuario usuarioActualizado = obtenerUsuarioActualizado();
        nombreField.setText(usuarioActualizado.getNombre());
        apellidoField.setText(usuarioActualizado.getApellido());
        emailField.setText(usuarioActualizado.getEmail());
        direccionField.setText(usuarioActualizado.getDireccion());
        fechaNacimientoLabel.setText(usuarioActualizado.getFechaNacimiento().format(formatoFecha));
        edadLabel.setText(String.valueOf(usuarioActualizado.getEdad()) + " años");
        saldoLabel.setText(String.format("$%.2f MXN", usuarioActualizado.getSaldo()));
    }

    private void inicializarComponentes() {
        JPanel panelNorte = new JPanel();
        JLabel titulo = new JLabel("Mi perfil");
        titulo.setFont(new Font("Dialog", Font.BOLD, 24));
        panelNorte.add(titulo);
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        emailField = new JTextField(20);
        direccionField = new JTextField(30);
        fechaNacimientoLabel = new JLabel();
        edadLabel = new JLabel();
        saldoLabel = new JLabel();

        int fila = 0;

        agregarCampo(panelCentral, "Nombre:", nombreField, gbc, fila++);
        agregarCampo(panelCentral, "Apellido:", apellidoField, gbc, fila++);
        agregarCampo(panelCentral, "Email:", emailField, gbc, fila++);
        agregarCampo(panelCentral, "Dirección:", direccionField, gbc, fila++);
        agregarCampo(panelCentral, "Fecha de nacimiento:", fechaNacimientoLabel, gbc, fila++);
        agregarCampo(panelCentral, "Edad:", edadLabel, gbc, fila++);
        agregarCampo(panelCentral, "Saldo actual:", saldoLabel, gbc, fila);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        JButton guardarButton = new JButton("Guardar cambios");
        guardarButton.addActionListener(e -> guardarCambios());
        panelSur.add(guardarButton);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo,
            GridBagConstraints gbc, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void cargarDatosUsuario() {
        refrescarDatos();

        fechaNacimientoLabel.setEnabled(false);
        edadLabel.setEnabled(false);
        saldoLabel.setEnabled(false);
    }

    private void guardarCambios() {
        if (nombreField.getText().trim().isEmpty()
                || apellidoField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || direccionField.getText().trim().isEmpty()) {
            DialogosUtil.mostrarError(this, "Todos los campos son requeridos");
            return;
        }

        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            DialogosUtil.mostrarError(this, "El formato del email no es válido");
            return;
        }

        try {
            ObtenerUsuarioDTO datos = new ObtenerUsuarioDTO(
                    usuario.getId(),
                    nombreField.getText().trim(),
                    apellidoField.getText().trim(),
                    direccionField.getText().trim(),
                    emailField.getText().trim()
            );

            controlUsuarios.actualizarDatosUsuario(datos);
            DialogosUtil.mostrarExito(this, "Datos actualizados exitosamente");

        } catch (Exception e) {
            DialogosUtil.mostrarError(this, "Error al actualizar los datos: " + e.getMessage());
        }
    }
}
