package edu.student.itson.bda.ticketwizard.persistencia;

import edu.student.itson.bda.ticketwizard.entidades.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EventosDAO {

    public List<Evento> consultarEventos() {
        String codigoSQL = """
                            SELECT
                                id,
                                nombre,
                                fecha,
                                recinto,
                                ciudad,
                                estado,
                                descripcion
                           FROM eventos;
                           """;
        List<Evento> listaEventos = new LinkedList<>();
        try {
            Connection conexion = ManejadorConexiones.crearConexion();
            PreparedStatement comando = conexion.prepareStatement(codigoSQL);
            ResultSet resultadosConsulta = comando.executeQuery();
            // nos movemos a cada una de las filas devueltas
            while (resultadosConsulta.next()) {
                // estamos dentro de una fila
                Integer codigo = resultadosConsulta.getInt("id");
                String nombre = resultadosConsulta.getString("nombre");
                String fecha = resultadosConsulta.getString("fecha");
                String recinto = resultadosConsulta.getString("recinto");
                String ciudad = resultadosConsulta.getString("ciudad");
                String estado = resultadosConsulta.getString("estado");
                String descripcion = resultadosConsulta.getString("descripcion");

                // paquete donde empaquetaras los datos
                Evento evento = new Evento(codigo, nombre, fecha, recinto, ciudad, estado, descripcion);
                listaEventos.add(evento);
            }

        } catch (SQLException ex) {
            System.err.println("Erros la consultar los eventos: " + ex.getMessage());
        }
        return listaEventos;
    }
}
