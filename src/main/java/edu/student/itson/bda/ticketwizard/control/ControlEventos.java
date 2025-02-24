package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.entidades.Evento;
import edu.student.itson.bda.ticketwizard.persistencia.EventosDAO;
import java.util.List;

public class ControlEventos {

    private EventosDAO eventosDAO;
    private ControlUsuarios usuarios;
    private ControlEventos controlEventos;

    public ControlEventos(EventosDAO eventosDAO, ControlUsuarios usuarios) {
        this.eventosDAO = eventosDAO;
        this.usuarios = usuarios;
    }

    public List<Evento> consultarListaEventos() {
        return this.eventosDAO.consultarEventos();
    }
}
