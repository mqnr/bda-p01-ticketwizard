package edu.student.itson.bda.ticketwizard.presentacion;

import edu.student.itson.bda.ticketwizard.control.ControlUsuarios;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import javax.swing.JPanel;

public abstract class PanelBaseUsuario extends JPanel implements PanelActualizable, PanelUsuario {

    protected final Usuario usuario;
    protected final ControlUsuarios controlUsuarios;

    public PanelBaseUsuario(ControlUsuarios controlUsuarios, Usuario usuario) {
        this.usuario = usuario;
        this.controlUsuarios = controlUsuarios;
    }

    @Override
    public Usuario obtenerUsuario() {
        return usuario;
    }

    @Override
    public ControlUsuarios obtenerControlUsuarios() {
        return controlUsuarios;
    }

    protected Usuario obtenerUsuarioActualizado() {
        return controlUsuarios.obtenerUsuario(
                new ObtenerUsuarioIdDTO(usuario.getId())
        );
    }
}
