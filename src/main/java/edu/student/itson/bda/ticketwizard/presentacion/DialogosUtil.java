package edu.student.itson.bda.ticketwizard.presentacion;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class DialogosUtil {

    public static void mostrarError(JComponent componente, String mensaje) {
        JOptionPane.showMessageDialog(
                componente,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void mostrarExito(JComponent componente, String mensaje) {
        JOptionPane.showMessageDialog(
                componente,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static boolean confirmar(JComponent componente, String mensaje) {
        return JOptionPane.showConfirmDialog(
                componente,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    public static void mostrarAdvertencia(JComponent componente, String mensaje) {
        JOptionPane.showMessageDialog(
                componente,
                mensaje,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
