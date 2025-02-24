package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.entidades.Transaccion;
import edu.student.itson.bda.ticketwizard.persistencia.TransaccionesDAO;
import java.util.List;

public class TransaccionesControl {
    private final TransaccionesDAO transaccionesDAO;
    
    public TransaccionesControl(TransaccionesDAO transaccionesDAO) {
        this.transaccionesDAO = transaccionesDAO;
    }
    
    public List<Transaccion> consultarTransaccionesUsuario(ObtenerUsuarioIdDTO datos) {
        return transaccionesDAO.consultarTransaccionesUsuario(datos.getIdUsuario());
    }
}
