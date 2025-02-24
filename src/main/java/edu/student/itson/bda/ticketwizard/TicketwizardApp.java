package edu.student.itson.bda.ticketwizard;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.LoginFrame;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TicketwizardApp {

    public static void main(String[] args) {
        // la GUI alternativa
        if (new File(".gui_alternativa").exists()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo cargar el tema del sistema. Se usará el tema por defecto.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        } else {
            // la GUI principal
            UsuariosDAO usuariosDAO = new UsuariosDAO();
            ControlUsuarios control = new ControlUsuarios(usuariosDAO);
            control.iniciarCasoUso();
        }
    }
}
