package edu.student.itson.bda.ticketwizard.persistencia;

import edu.student.itson.bda.ticketwizard.dtos.TransaccionDetalleDTO;
import edu.student.itson.bda.ticketwizard.entidades.Transaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TransaccionesDAO {

    public List<TransaccionDetalleDTO> consultarTransaccionesUsuario(int idUsuario) {
        String sql = """
            SELECT t.*,
                   b.numero_serie, b.fila, b.asiento,
                   e.nombre as evento_nombre, e.fecha as evento_fecha,
                   e.recinto as evento_recinto
            FROM transacciones t
            JOIN boletos b ON t.boleto_id = b.numero_serie
            JOIN eventos e ON b.evento_id = e.id
            WHERE t.comprador_id = ? OR t.vendedor_id = ?
            ORDER BY t.fecha DESC
        """;

        List<TransaccionDetalleDTO> transacciones = new ArrayList<>();

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql)) {

            comando.setInt(1, idUsuario);
            comando.setInt(2, idUsuario);

            try (ResultSet rs = comando.executeQuery()) {
                while (rs.next()) {
                    transacciones.add(new TransaccionDetalleDTO(
                            rs.getInt("id"),
                            Transaccion.TipoAdquisicion.valueOf(rs.getString("tipo_adquisicion")),
                            (Integer) rs.getObject("vendedor_id"), // Puede ser NULL
                            rs.getInt("comprador_id"),
                            rs.getBigDecimal("precio"),
                            rs.getBigDecimal("comision"), // Puede ser NULL para ventas iniciales
                            rs.getTimestamp("fecha").toLocalDateTime(),
                            rs.getInt("numero_serie"),
                            rs.getInt("fila"),
                            rs.getString("asiento"),
                            rs.getString("evento_nombre"),
                            rs.getDate("evento_fecha").toLocalDate(),
                            rs.getString("evento_recinto")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar transacciones", e);
        }

        return transacciones;
    }

    public void registrarTransaccion(Transaccion transaccion) {
        String sql = """
            INSERT INTO transacciones 
            (boleto_id, tipo_adquisicion, vendedor_id, comprador_id, precio, comision, fecha)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conexion = ManejadorConexiones.crearConexion(); PreparedStatement comando = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setInt(1, transaccion.getBoletoId());
            comando.setString(2, transaccion.getTipoAdquisicion().toString());
            if (transaccion.getVendedorId() != null) {
                comando.setInt(3, transaccion.getVendedorId());
            } else {
                comando.setNull(3, Types.INTEGER);
            }
            comando.setInt(4, transaccion.getCompradorId());
            comando.setBigDecimal(5, transaccion.getPrecio());
            if (transaccion.getComision() != null) {
                comando.setBigDecimal(6, transaccion.getComision());
            } else {
                comando.setNull(6, Types.DECIMAL);
            }
            comando.setTimestamp(7, Timestamp.valueOf(transaccion.getFecha()));

            comando.executeUpdate();

            try (ResultSet generatedKeys = comando.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaccion.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar transacción", e);
        }
    }
}
