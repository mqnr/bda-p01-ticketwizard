package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.AgregarSaldoDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.AgregarSaldo;
import edu.student.itson.bda.ticketwizard.presentacion.InicioPerfil;
import java.math.BigDecimal;

public class ControlUsuarios {

    private final UsuariosDAO usuariosDAO;
    private InicioPerfil formInicioPerfil;
    private AgregarSaldo formAgregarSaldo;
    private int idUsuario;

    public ControlUsuarios(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    public void iniciarCasoUso() {
        idUsuario = consultarUsuarioAleatorio().getId();
        this.formInicioPerfil = new InicioPerfil(this);
        this.formInicioPerfil.setVisible(true);
    }

    public void agregarSaldo(BigDecimal monto) {
        this.usuariosDAO.agregarSaldo(new AgregarSaldoDTO(idUsuario, monto));
    }

    public Usuario consultarUsuario() {
        return this.usuariosDAO.consultarUsuario(new ObtenerUsuarioDTO(idUsuario));
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
