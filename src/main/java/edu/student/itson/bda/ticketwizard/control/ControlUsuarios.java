package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.AgregarSaldoDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import java.math.BigDecimal;

public class ControlUsuarios {

    private final UsuariosDAO usuariosDAO;
    private int idUsuario;

    public ControlUsuarios(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void iniciarCasoUso() {
        idUsuario = consultarUsuarioAleatorio().getId();
    }

    public void agregarSaldo(AgregarSaldoDTO datos) {
        if (datos.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        usuariosDAO.agregarSaldo(datos.getIdUsuario(), datos.getMonto());
    }

    public Usuario obtenerUsuario(ObtenerUsuarioIdDTO datos) {
        return this.usuariosDAO.consultarUsuario(datos.getIdUsuario());
    }

    public Usuario consultarUsuario() {
        return this.usuariosDAO.consultarUsuario(idUsuario);
    }

    public Usuario consultarUsuarioAleatorio() {
        return this.usuariosDAO.consultarUsuarioAleatorio();
    }

    public void actualizarDatosUsuario(ObtenerUsuarioDTO datos) {
        usuariosDAO.actualizarDatosUsuario(datos.getIdUsuario(), datos.getNombre(), datos.getApellido(), datos.getDireccion(), datos.getEmail());
    }
}
