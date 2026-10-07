package org.example.carmilaglow.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:sqlite:carmilaglow.db";

    private Conexion() {
    }

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);

        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con SQLite.", e);
        }
    }
}
