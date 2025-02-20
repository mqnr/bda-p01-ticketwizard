package edu.student.itson.bda.ticketwizard.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ManejadorConexiones {

    public static Connection crearConexion() throws SQLException {
        Connection conexion;
        Properties props = new Properties();
        try (InputStream is = ManejadorConexiones.class.getResourceAsStream("/database.properties")) {
            if (is == null) {
                throw new IOException("No se pudo encontrar database.properties. ¿Lo creaste a partir de database.properties.example?");
            }
            props.load(is);

            conexion = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (IOException e) {
            throw new SQLException("Error al cargar propiedades de la base de datos: " + e.getMessage(), e);
        }
        return conexion;
    }
}
