package edu.student.itson.bda.ticketwizard.persistencia;

import edu.student.itson.bda.ticketwizard.entidades.Usuario;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {

    public void agregarSaldo(int idUsuario, BigDecimal monto) {
        String codigoSQL = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";

        try {
            Connection conexion = ManejadorConexiones.crearConexion();

            PreparedStatement comando = conexion.prepareStatement(codigoSQL);

            comando.setBigDecimal(1, monto);
            comando.setInt(2, idUsuario);

            int filasAfectadas = comando.executeUpdate();
            if (filasAfectadas == 0) {
                throw new RuntimeException("Usuario no encontrado: " + idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar saldo", e);
        }
    }

    public Usuario consultarUsuario(int idUsuario) {
        String codigoSQL = """
                           SELECT
                             id,
                             email,
                             nombre,
                             apellido,
                             direccion,
                             fecha_nacimiento,
                             saldo
                           FROM usuarios WHERE id = ?
                           """;

        try {
            Connection conexion = ManejadorConexiones.crearConexion();

            PreparedStatement comando = conexion.prepareStatement(codigoSQL);

            comando.setInt(1, idUsuario);

            ResultSet resultadosConsulta = comando.executeQuery();
            if (resultadosConsulta.next()) {
                return new Usuario(
                        resultadosConsulta.getInt("id"),
                        resultadosConsulta.getString("nombre"),
                        resultadosConsulta.getString("apellido"),
                        resultadosConsulta.getString("direccion"),
                        resultadosConsulta.getString("email"),
                        resultadosConsulta.getBigDecimal("saldo"),
                        resultadosConsulta.getDate("fecha_nacimiento").toLocalDate()
                );
            } else {
                throw new RuntimeException("Usuario no encontrado: " + idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar saldo", e);
        }
    }

    public BigDecimal consultarSaldo(int idUsuario) {
        String codigoSQL = "SELECT saldo FROM usuarios WHERE id = ?";

        try {
            Connection conexion = ManejadorConexiones.crearConexion();

            PreparedStatement comando = conexion.prepareStatement(codigoSQL);

            comando.setInt(1, idUsuario);

            ResultSet resultadosConsulta = comando.executeQuery();
            if (resultadosConsulta.next()) {
                return resultadosConsulta.getBigDecimal("saldo");
            } else {
                throw new RuntimeException("Usuario no encontrado: " + idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar saldo", e);
        }
    }

    public Usuario consultarUsuarioAleatorio() {
        String codigoSQL = """
                           SELECT
                             id,
                             email,
                             nombre,
                             apellido,
                             direccion,
                             fecha_nacimiento,
                             saldo
                           FROM usuarios
                           ORDER BY RAND() LIMIT 1
                           """;

        try {
            Connection conexion = ManejadorConexiones.crearConexion();

            PreparedStatement comando = conexion.prepareStatement(codigoSQL);

            ResultSet resultadosConsulta = comando.executeQuery();
            if (resultadosConsulta.next()) {
                return new Usuario(
                        resultadosConsulta.getInt("id"),
                        resultadosConsulta.getString("nombre"),
                        resultadosConsulta.getString("apellido"),
                        resultadosConsulta.getString("direccion"),
                        resultadosConsulta.getString("email"),
                        resultadosConsulta.getBigDecimal("saldo"),
                        resultadosConsulta.getDate("fecha_nacimiento").toLocalDate()
                );
            } else {
                throw new RuntimeException("No hay usuarios en la base de datos");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar usuario aleatorio", e);
        }
    }
}
