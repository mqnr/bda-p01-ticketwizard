package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.AgregarSaldoDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioDTO;
import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.UsuariosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.AgregarSaldo;
import edu.student.itson.bda.ticketwizard.presentacion.InicioPerfil;
import edu.student.itson.bda.ticketwizard.presentacion.MiCuenta;
import java.math.BigDecimal;

public class ControlUsuarios {

    private final UsuariosDAO usuariosDAO;
    private InicioPerfil formInicioPerfil;
    private AgregarSaldo formAgregarSaldo;
    private MiCuenta formMiCuenta;
    private int idUsuario;

    public ControlUsuarios(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void iniciarCasoUso() {
        idUsuario = consultarUsuarioAleatorio().getId();
        this.formInicioPerfil = new InicioPerfil(this);
        this.formInicioPerfil.setVisible(true);
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

    public void mostrarFormularioAgregarSaldo() {
        this.formInicioPerfil.dispose();
        this.formAgregarSaldo = new AgregarSaldo(this);
        this.formAgregarSaldo.setVisible(true);
    }

    public void mostrarFormularioInicioPerfil() {
        if (this.formAgregarSaldo != null) {
            this.formAgregarSaldo.dispose();
        } else if (this.formMiCuenta != null) {
            this.formMiCuenta.dispose();
        }
        this.formInicioPerfil.establecerInformacionUsuario();
        this.formInicioPerfil.setVisible(true);
    }

    public void mostrarFormularioMiCuenta() {
        this.formInicioPerfil.dispose();
        this.formMiCuenta = new MiCuenta(this);
        this.formMiCuenta.setVisible(true);
    }
    public void actualizarDatosUsuario(ObtenerUsuarioDTO datos) {
        usuariosDAO.actualizarDatosUsuario(datos.getIdUsuario(), datos.getNombre(), datos.getApellido(), datos.getDireccion(), datos.getEmail());
    }
}
