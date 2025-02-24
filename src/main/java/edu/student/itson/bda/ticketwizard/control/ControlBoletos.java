package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.entidades.Boleto;
import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import edu.student.itson.bda.ticketwizard.persistencia.BoletosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.BoletosComprados;
import edu.student.itson.bda.ticketwizard.presentacion.InicioPerfil;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ControlBoletos {

    private final BoletosDAO boletosDAO;
    private BoletosComprados formCatalogoBoletos;
    private final Usuario usuario;
    private InicioPerfil formInicioPerfil;
    private ControlUsuarios controlUsuario;
    
    public ControlBoletos(BoletosDAO boletosDAO, Usuario usuario) {
        this.boletosDAO = boletosDAO;
        this.usuario = usuario;
        this.controlUsuario = controlUsuario;
    }

    public void iniciarCasoUso() {
        this.formCatalogoBoletos = new BoletosComprados(this, this.usuario);
        this.formCatalogoBoletos.setVisible(true);
    }

    public void buscarBoletos() {
        List<Boleto> boletos = this.boletosDAO.obtenerTodosBoletos();
        this.mostrarFormularioBoletosDisponibles();
    }

    public void mostrarFormularioBoletosDisponibles() {
        this.formInicioPerfil.dispose();
        this.formCatalogoBoletos = new BoletosComprados(this, this.usuario);
        this.formCatalogoBoletos.setVisible(true);
    }


    public void mostrarFormularioInicioPerfil() {
        this.formCatalogoBoletos.dispose();
        this.formInicioPerfil = new InicioPerfil(this.controlUsuario);
        this.formInicioPerfil.setVisible(true);
    }

    public List<Boleto> consultarListaBoletos() {
        return this.boletosDAO.obtenerTodosBoletos();
    }
}
