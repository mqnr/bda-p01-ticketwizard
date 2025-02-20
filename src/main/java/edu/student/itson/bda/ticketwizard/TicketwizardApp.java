package edu.student.itson.bda.ticketwizard;

import edu.student.itson.bda.ticketwizard.control.ControlAgregarSaldo;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;

public class TicketwizardApp {

    public static void main(String[] args) {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        ControlAgregarSaldo control = new ControlAgregarSaldo(usuariosDAO);
        control.iniciarCasoUso();
    }
}
