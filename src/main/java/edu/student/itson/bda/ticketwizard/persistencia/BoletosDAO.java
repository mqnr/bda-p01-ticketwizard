package edu.student.itson.bda.ticketwizard.persistencia;

import edu.student.itson.bda.ticketwizard.entidades.Boleto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class BoletosDAO {
        public List<Boleto> consultarMisBoletos() {
        String codigoSQL = """
                            SELECT
                                id,
                                numero_serie,
                                precio,
                                fila,
                                asiento,
                                id_asiento,
                                usuario_id,
                                evento_id
                           FROM boletos;
                           """;
        List<Boleto> listaBoletos = new LinkedList<>();
        try {
            Connection conexion = ManejadorConexiones.crearConexion();
            PreparedStatement comando = conexion.prepareStatement(codigoSQL);
            ResultSet resultadosConsulta = comando.executeQuery();
            // nos movemos a cada una de las filas devueltas
            while (resultadosConsulta.next()) {
                // estamos dentro de una fila
                Integer codigo = resultadosConsulta.getInt("id");
                String numeroSerie = resultadosConsulta.getString("numero_serie");
                BigDecimal precio = resultadosConsulta.getBigDecimal("precio");
                int fila = resultadosConsulta.getInt("fila");
                String asiento = resultadosConsulta.getString("asiento");
                int idAsiento = resultadosConsulta.getInt("id_asiento");
                int usuarioId = resultadosConsulta.getInt("usuario_Id");
                int eventoId = resultadosConsulta.getInt("evento_id");

                Boleto boleto = new Boleto(codigo, numeroSerie, precio, fila, asiento, idAsiento, usuarioId, eventoId);
                listaBoletos.add(boleto);
            }

        } catch (SQLException ex) {
            System.err.println("Erros la consultar los boletos: " + ex.getMessage());
        }
        return listaBoletos;
    }
}
