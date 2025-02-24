package edu.student.itson.bda.ticketwizard.persistencia;

import edu.student.itson.bda.ticketwizard.dtos.BoletoDisponibleDTO;
import edu.student.itson.bda.ticketwizard.entidades.Boleto;
import edu.student.itson.bda.ticketwizard.entidades.Transaccion.TipoAdquisicion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author LENOVO
 */
public class BoletosDAO {

    public List<Boleto> obtenerBoletosUsuario(int idUsuario) {
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
                           FROM boletos
                           WHERE usuario_id = ?
                           """;
        List<Boleto> listaBoletos = new LinkedList<>();

        try {
            Connection conexion = ManejadorConexiones.crearConexion();

            PreparedStatement comando = conexion.prepareStatement(codigoSQL);

            comando.setInt(1, idUsuario);

            ResultSet resultadosConsulta = comando.executeQuery();
            while (resultadosConsulta.next()) {
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

    public List<Boleto> obtenerTodosBoletos() {
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
                           FROM boletos
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

    public Optional<BoletoDisponibleDTO> consultarBoletoDisponible(int numeroSerie) {
        String sql = """
            SELECT 
                b.numero_serie, b.precio as precio_original, b.fila, b.asiento,
                e.nombre as evento_nombre, e.fecha as evento_fecha, 
                e.recinto as recinto_evento, e.ciudad as ciudad_evento,
                e.estado as estado_evento,
                CASE 
                    WHEN br.boleto_id IS NULL THEN b.precio
                    ELSE br.precio_reventa
                END as precio_venta,
                br.vendedor_id, br.fecha_limite,
                CASE 
                    WHEN br.vendedor_id IS NULL THEN 'Boletera'
                    ELSE CONCAT(u.nombre, ' ', u.apellido)
                END as nombre_vendedor,
                CASE 
                    WHEN br.boleto_id IS NULL THEN 'VENTA_INICIAL'
                    ELSE 'REVENTA'
                END as tipo_venta
            FROM boletos b
            JOIN eventos e ON b.evento_id = e.id
            LEFT JOIN boletos_reventa br ON b.numero_serie = br.boleto_id
            LEFT JOIN usuarios u ON br.vendedor_id = u.id
            WHERE b.numero_serie = ?
            AND NOT EXISTS (
                SELECT 1 FROM reservaciones r 
                WHERE r.boleto_id = b.numero_serie 
                AND r.fecha_expiracion > NOW()
            )
        """;

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, numeroSerie);

            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new BoletoDisponibleDTO(
                            rs.getInt("numero_serie"),
                            rs.getBigDecimal("precio_original"),
                            rs.getBigDecimal("precio_venta"),
                            rs.getInt("fila"),
                            rs.getString("asiento"),
                            rs.getString("evento_nombre"),
                            rs.getDate("evento_fecha").toLocalDate(),
                            rs.getString("recinto_evento"),
                            rs.getString("ciudad_evento"),
                            rs.getString("estado_evento"),
                            (Integer) rs.getObject("vendedor_id"),
                            rs.getString("nombre_vendedor"),
                            rs.getObject("fecha_limite") != null
                            ? rs.getDate("fecha_limite").toLocalDate() : null,
                            TipoAdquisicion.valueOf(rs.getString("tipo_venta"))
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar boleto", e);
        }

        return Optional.empty();
    }

    public List<BoletoDisponibleDTO> consultarBoletosDisponibles() {
        String sql = """
            -- Boletos en venta directa (de la boletera)
            SELECT 
                b.numero_serie, b.precio as precio_original, b.fila, b.asiento,
                e.nombre as evento_nombre, e.fecha as evento_fecha, 
                e.recinto as recinto_evento, e.ciudad as ciudad_evento,
                e.estado as estado_evento,
                NULL as vendedor_id, b.precio as precio_venta,
                NULL as fecha_limite, 'Boletera' as nombre_vendedor,
                'VENTA_INICIAL' as tipo_venta
            FROM boletos b
            JOIN eventos e ON b.evento_id = e.id
            WHERE b.usuario_id IS NULL
            AND NOT EXISTS (
                SELECT 1 FROM reservaciones r 
                WHERE r.boleto_id = b.numero_serie 
                AND r.fecha_expiracion > NOW()
            )
            
            UNION ALL
            
            -- Boletos en reventa
            SELECT 
                b.numero_serie, b.precio as precio_original, b.fila, b.asiento,
                e.nombre as evento_nombre, e.fecha as evento_fecha, 
                e.recinto as recinto_evento, e.ciudad as ciudad_evento,
                e.estado as estado_evento,
                br.vendedor_id, br.precio_reventa as precio_venta,
                br.fecha_limite,
                CONCAT(u.nombre, ' ', u.apellido) as nombre_vendedor,
                'REVENTA' as tipo_venta
            FROM boletos_reventa br
            JOIN boletos b ON br.boleto_id = b.numero_serie
            JOIN eventos e ON b.evento_id = e.id
            JOIN usuarios u ON br.vendedor_id = u.id
            WHERE NOT EXISTS (
                SELECT 1 FROM reservaciones r 
                WHERE r.boleto_id = br.boleto_id 
                AND r.fecha_expiracion > NOW()
            )
            
            ORDER BY evento_fecha ASC, tipo_venta, numero_serie
        """;

        List<BoletoDisponibleDTO> boletosDisponibles = new ArrayList<>();

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BoletoDisponibleDTO boleto = new BoletoDisponibleDTO(
                            rs.getInt("numero_serie"),
                            rs.getBigDecimal("precio_original"),
                            rs.getBigDecimal("precio_venta"),
                            rs.getInt("fila"),
                            rs.getString("asiento"),
                            rs.getString("evento_nombre"),
                            rs.getDate("evento_fecha").toLocalDate(),
                            rs.getString("recinto_evento"),
                            rs.getString("ciudad_evento"),
                            rs.getString("estado_evento"),
                            (Integer) rs.getObject("vendedor_id"),
                            rs.getString("nombre_vendedor"),
                            rs.getObject("fecha_limite") != null
                            ? rs.getDate("fecha_limite").toLocalDate() : null,
                            TipoAdquisicion.valueOf(rs.getString("tipo_venta"))
                    );
                    boletosDisponibles.add(boleto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar boletos en reventa", e);
        }

        return boletosDisponibles;
    }

    public void reservarBoleto(int boletoId, int usuarioId) {
        String sql = """
            INSERT INTO reservaciones (boleto_id, usuario_id, fecha_reserva, fecha_expiracion)
            VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 10 MINUTE))
        """;

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, boletoId);
            comando.setInt(2, usuarioId);
            comando.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al reservar boleto", e);
        }
    }

    public void liberarReservacion(int boletoId) {
        String sql = "DELETE FROM reservaciones WHERE boleto_id = ?";

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, boletoId);
            comando.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al liberar reservación", e);
        }
    }

    public boolean estaReservado(int boletoId) {
        String sql = """
            SELECT COUNT(*) FROM reservaciones 
            WHERE boleto_id = ? AND fecha_expiracion > NOW()
        """;

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, boletoId);
            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar reservación", e);
        }

        return false;
    }

    public void actualizarPropietario(int numeroSerie, int nuevoUsuarioId) {
        String sql = "UPDATE boletos SET usuario_id = ? WHERE numero_serie = ?";

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, nuevoUsuarioId);
            comando.setInt(2, numeroSerie);

            int filasAfectadas = comando.executeUpdate();
            if (filasAfectadas == 0) {
                throw new RuntimeException("No se encontró el boleto: " + numeroSerie);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar propietario", e);
        }
    }

    public void eliminarReventa(int numeroSerie) {
        String sql = "DELETE FROM boletos_reventa WHERE boleto_id = ?";

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, numeroSerie);
            comando.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar reventa", e);
        }
    }
}
