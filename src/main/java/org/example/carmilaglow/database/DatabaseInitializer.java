package org.example.carmilaglow.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void crearTablaProducto() {
        String sql = """
                CREATE TABLE IF NOT EXISTS producto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo TEXT NOT NULL UNIQUE,
                categoria_id INTEGER NOT NULL,
                nombre TEXT NOT NULL,
                marca TEXT NOT NULL,                
                descripcion TEXT NOT NULL,
                stock INTEGER DEFAULT 0 CHECK(stock >= 0),
                stock_minimo INTEGER NOT NULL DEFAULT 0 CHECK(stock_minimo >= 0),
                precio_costo REAL DEFAULT 0 CHECK(precio_costo >= 0),
                precio_publico REAL DEFAULT 0 CHECK(precio_publico >= 0),
                FOREIGN KEY (categoria_id) REFERENCES categoria(id)
                )
                """;

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

            System.out.println("Tabla producto creada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla producto.");
            e.printStackTrace();
        }
    }

    public static void crearTablaCategoria() {

        String sql = """
            
                CREATE TABLE IF NOT EXISTS categoria (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL UNIQUE,
                descripcion TEXT
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla categoria creada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear la tabla categoria.");
            e.printStackTrace();
        }
    }

    public static void crearTablaProveedor() {

        String sql =
                """
            CREATE TABLE IF NOT EXISTS proveedor (
                id INTEGER PRIMARY KEY
                AUTOINCREMENT,
                            nombre TEXT NOT NULL UNIQUE
                ,
                telefono TEXT,
                email TEXT,
                direccion TEXT
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla proveedor creada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear la tabla proveedor.");
            e.printStackTrace();
        }
    }

    public static void
                crearTablaMovimiento() {

        String sql
                = """
            CREATE TABLE IF NOT
                EXISTS movimiento (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                producto_id INTEGER
                NOT NULL,
                tipo TEXT NOT NULL CHECK(
                tipo IN ('ENTRADA',
                'SALIDA')),
                cantidad INTEGER NOT NULL CHECK(cantidad > 0),
                            fecha
                TEXT NOT NULL,
                observacion TEXT,
                FOREIGN KEY (producto_id) REFERENCES producto(id)
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla movimiento creada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear la tabla movimiento.");
            e.printStackTrace();
        }
    }

    public static void crearTablaUsuario() {

        String sql =
                """
            CREATE TABLE IF NOT
                EXISTS usuario (
                id INTEGER PRIMARY KEY
                            AUTOINCREMENT,
                nombre_usuario TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                rol TEXT NOT NULL CHECK(rol IN ('ADMIN', 'CONSULTAR'))
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla usuario creada correctamente.");

        } catch (
                Exception e) {
            System.out.
                println("Error al crear la tabla usuario.");
            e.printStackTrace();
        }
    }

    public static void
                crearTablaCompra() {

        String
                sql = """
            
                CREATE TABLE IF NOT EXISTS compra (
                id
                            INTEGER PRIMARY KEY AUTOINCREMENT,
                proveedor_id INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                total REAL NOT NULL DEFAULT 0,
                observacion TEXT,
                FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla compra creada correctamente");

        } catch (Exception e) {
                System.out.println("Error al crear la tabala compra");
            e.printStackTrace();
        }
    }


    public static void crearTablaDetalleCompra() {

        String sql = """
            CREATE TABLE IF NOT EXISTS
                detalle_compra (
                id INTEGER PRIMARY KEY
                AUTOINCREMENT,
                compra_id INTEGER NOT
                NULL,
                producto_id INTEGER NO
                            cantidad INTEGER NOT NULL CHECK(cantidad > 0),
                precio_unitario REAL NOT NULL CHECK(precio_unitario >= 0),
                subtotal REAL NOT NULL CHECK(subtotal >= 0),
                FOREIGN KEY (compra_id) REFERENCES compra(id),
                FOREIGN KEY (producto_id) REFERENCES producto(id)
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement statement = conexion.createStatement()) {

            statement.
                execute(sql);

            System.
                out.println("Tabla detalle_compra correctamente.");

        } catch (Exception e) {
            System.out.println(
                "Error al crear la tabla compra.");
            e.
                printStackTrace();
        }
    }

    public static void
                crearTablaVenta() {

        String sql = """
            CREATE TABLE IF NOT EXISTS venta (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                total REAL NOT NULL DEFAULT 0,
                observacion TEXT,
                FOREIGN KEY (cliente_id) REFERENCES cliente(id)
            )
            """;

        try (Connection conexion = Conexion.conectar();
                Statement statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.
                println("Tabla venta creada correctamente.");

        } catch (Exception e)
                {
            System.out.println("Error al crear tabla venta.");
            e.printStackTrace();
        }
    }
                public static void crearTablaDetalleVenta() {
                String sql = """
            CREATE TABLE IF NOT EXISTS
                detalle_venta (
                id INTEGER PRIMARY KEY
                            AUTOINCREMENT,
                venta_id INTEGER NOT NULL,
                producto_id INTEGER NOT NULL,
                cantidad INTEGER NOT NULL CHECK(cantidad > 0),
                precio_unitario REAL NOT NULL CHECK(precio_unitario >= 0),
                subtotal REAL NOT NULL CHECK(subtotal >= 0),
                FOREIGN KEY (venta_id) REFERENCES venta(id),
                FOREIGN KEY (producto_id) REFERENCES producto(id)
            )
            """;

        try (
                Connection conexion = Conexion.conectar()
                ;
             Statement statement =
                conexion.createStatement()) {

            statement.execute(sql);

            System.out.println(
                "Tabla detalle creada correctamente.");

        } catch (
                Exception e) {
            System.out.println("Error al crear la tabla detalle_venta.");
            e.printStackTrace();
        }
    }

    public static void crearTablaCliente() {

        String sql = """
            CREATE TABLE IF NOT EXISTS cliente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                documento TEXT NOT NULL UNIQUE,            telefono TEXT,
                email TEXT,
                direccion TEXT
            )
            """;

        try (Connection conexion = Conexion.conectar();
             Statement
             statement = conexion.createStatement()) {

            statement.execute(sql);

            System.out.println("Tabla cliente creada correctamente.");

        } catch (Exception e)

        {
            System.out.println("Error al crear la tabla cliente.");
            e.
    printStackTrace();
        }
    }

    public static void cambiarCategoria (int id, String nuevoNomnbre) {
        String sql = """
                UPDATE categoria SET nombre = ? WHERE id = ? 
                """;
        try (Connection conexion= Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, nuevoNomnbre);
            statement.setInt(2, id);

            int filas = statement.executeUpdate();

        System.out.println("Fila actualizada" + filas);

        } catch (Exception e) {
        System.out.println("Error al actualizar.");
        e.printStackTrace();
        }
    }
}
