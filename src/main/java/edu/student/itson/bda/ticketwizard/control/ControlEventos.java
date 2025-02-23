package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.entidades.Evento;
import edu.student.itson.bda.ticketwizard.persistencia.EventosDAO;
import edu.student.itson.bda.ticketwizard.presentacion.EventosDisponibles;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ControlEventos {

    private final EventosDAO eventosDAO;
    private EventosDisponibles formCatalogoEventos;

    public ControlEventos(EventosDAO eventosDAO) {
        this.eventosDAO = eventosDAO;
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
        this.formCatalogoEventos = new EventosDisponibles(this);
        this.formCatalogoEventos.setVisible(true);
    }

    public List<Evento> consultarListaEventos() {
        return this.eventosDAO.consultarEventos();
    }
}
