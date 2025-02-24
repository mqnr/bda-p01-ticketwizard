package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;

public interface PanelUsuario {

    Usuario obtenerUsuario();

    ControlUsuarios obtenerControlUsuarios();
}
