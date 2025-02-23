package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.AgregarSaldoDTO;
import edu.student.itson.bda.ticketwizard.dtos.BuscarUsuarioDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.AgregarSaldo;
import edu.student.itson.bda.ticketwizard.presentacion.InicioPerfil;
import java.math.BigDecimal;

public class ControlUsuarios {

    private final UsuariosDAO usuariosDAO;
    private InicioPerfil formInicioPerfil;
    private AgregarSaldo formAgregarSaldo;
    private final String usuarioCorreo;

    public ControlUsuarios(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
        Usuario usuario = consultarUsuarioAleatorio();
        this.usuarioCorreo = usuario.getEmail();
    }

    public void iniciarCasoUso() {
        this.formInicioPerfil = new InicioPerfil(this);
        this.formInicioPerfil.setVisible(true);
    }

    public void agregarSaldo(BigDecimal monto) {
        this.usuariosDAO.agregarSaldo(new AgregarSaldoDTO(usuarioCorreo, monto));
    }

    public Usuario consultarUsuario() {
        return this.usuariosDAO.consultarUsuario(new BuscarUsuarioDTO(usuarioCorreo));
    }

    public Usuario consultarUsuarioAleatorio() {
        return this.usuariosDAO.consultarUsuarioAleatorio();
    }

    public void mostrarFormularioAgregarSaldo() {
        this.formInicioPerfil.dispose();
        this.formAgregarSaldo = new AgregarSaldo(this);
        this.formAgregarSaldo.setVisible(true);
    }

    public void mostrarFormularioInicioPerfil() {
        this.formAgregarSaldo.dispose();
        this.formInicioPerfil.establecerInformacionUsuario();
        this.formInicioPerfil.setVisible(true);
    }
}
