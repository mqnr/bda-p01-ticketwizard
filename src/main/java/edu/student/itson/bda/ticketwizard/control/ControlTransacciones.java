package edu.student.itson.bda.ticketwizard.control;

import edu.student.itson.bda.ticketwizard.dtos.ObtenerUsuarioIdDTO;
import edu.student.itson.bda.ticketwizard.dtos.TransaccionDetalleDTO;
import edu.student.itson.bda.ticketwizard.persistencia.TransaccionesDAO;
import java.util.List;

public class ControlTransacciones {
    private final TransaccionesDAO transaccionesDAO;
    
    public ControlTransacciones(TransaccionesDAO transaccionesDAO) {
        this.transaccionesDAO = transaccionesDAO;
    }
    
    public List<TransaccionDetalleDTO> consultarTransaccionesUsuario(ObtenerUsuarioIdDTO dto) {
        return transaccionesDAO.consultarTransaccionesUsuario(dto.getIdUsuario());
    }
}
