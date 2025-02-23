package edu.student.itson.bda.ticketwizard;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;

public class TicketwizardApp {

    public static void main(String[] args) {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        ControlUsuarios control = new ControlUsuarios(usuariosDAO);
        control.iniciarCasoUso();
    }
}
