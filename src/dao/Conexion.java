package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_base_datos";  // Reemplaza con el nombre de tu base de datos
    private static final String USER = "root";  // Usuario para la conexión
    private static final String PASSWORD = "";  // Contraseña de la base de datos

    private Connection con;

    public Conexion() {
        // Constructor vacío
    }

    public Connection getConnection() throws SQLException {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw e;  // Lanza la excepción para que sea manejada en otro lugar
        }
        return con;
    }

    // Cerrar la conexión (opcional pero recomendado)
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}