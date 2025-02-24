package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.entidades.Evento;
import edu.student.itson.bda.ticketwizard.persistencia.EventosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.EventosDisponibles;
import edu.student.itson.bda.ticketwizard.presentacion.InicioPerfil;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ControlEventos {

    private EventosDAO eventosDAO;
    private EventosDisponibles formCatalogoEventos;
    private InicioPerfil formInicioPerfil;
    private ControlUsuarios usuarios;
    private ControlEventos controlEventos;

    public ControlEventos(EventosDAO eventosDAO, ControlUsuarios usuarios) {
        this.eventosDAO = eventosDAO;
        this.usuarios = usuarios;
    }

    public void iniciarCasoUso() {
        this.formCatalogoEventos = new EventosDisponibles(this);
        this.formCatalogoEventos.setVisible(true);
    }

    public void buscarEventos() {
        List<Evento> evento = this.eventosDAO.consultarEventos();
        this.mostrarFormularioEventosDisponibles();
    }

    public void mostrarFormularioEventosDisponibles() {
        this.formInicioPerfil.dispose();
        this.formCatalogoEventos = new EventosDisponibles(this.controlEventos);
        this.formCatalogoEventos.setVisible(true);
    }

    public void mostrarFormularioInicioPerfil() {
        this.formCatalogoEventos.dispose();
        this.formInicioPerfil = new InicioPerfil(this.usuarios);
        this.formInicioPerfil.setVisible(true);
    }

    public List<Evento> consultarListaEventos() {
        return this.eventosDAO.consultarEventos();
    }
}
