package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    //INSERTAR PRODUCTOS
    public boolean insertar(Producto producto) {

        String sql = """
                INSERT INTO producto
                (codigo, nombre, marca, descripcion, categoria_id, stock, stock_minimo,
                 precio_costo, precio_publico)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getCodigo());
            statement.setString(2, producto.getNombre());
            statement.setString(3, producto.getMarca());
            statement.setString(4, producto.getDescripcion());
            statement.setInt(5, producto.getCategoriaId());
            statement.setInt(6, producto.getStock());
            statement.setInt(7, producto.getStockMinimo());
            statement.setInt(8, producto.getPrecioCosto());
            statement.setInt(9, producto.getPrecioPublico());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar producto.");
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<Producto> listar() {

        List<Producto> productos = new ArrayList<>();

        String sql = """
                SELECT id, codigo, nombre, marca, descripcion, categoria_id,
                       stock, stock_minimo, precio_costo, precio_publico
                FROM producto
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("codigo"),
                        resultado.getString("nombre"),
                        resultado.getString("marca"),
                        resultado.getString("descripcion"),
                        resultado.getInt("categoria_id"),
                        resultado.getInt("stock"),
                        resultado.getInt("stock_minimo"),
                        resultado.getInt("precio_costo"),
                        resultado.getInt("precio_publico")
                );

                productos.add(producto);
            }

        } catch (Exception e) {
            System.out.println("Error al listar productos.");
            e.printStackTrace();
        }

        return productos;
    }

    // ACTUALIZAR
    public boolean actualizar(Producto producto) {

        String sql = """
                UPDATE producto
                SET codigo = ?,
                    nombre = ?,
                    marca = ?,
                    descripcion = ?,
                    categoria_id = ?,
                    stock = ?,
                    stock_minimo = ?,
                    precio_costo = ?,
                    precio_publico = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getCodigo());
            statement.setString(2, producto.getNombre());
            statement.setString(3, producto.getMarca());
            statement.setString(4, producto.getDescripcion());
            statement.setInt(5, producto.getCategoriaId());
            statement.setInt(6, producto.getStock());
            statement.setInt(7, producto.getStockMinimo());
            statement.setInt(8, producto.getPrecioCosto());
            statement.setInt(9, producto.getPrecioPublico());
            statement.setInt(10, producto.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar producto.");
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM producto WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar producto.");
            e.printStackTrace();
            return false;
        }
    }

    // AUMENTAR EL STOCK
    public boolean aumentarStock(
            Connection conexion,
            int productoId,
            int cantidad) {

        String sql = """
            UPDATE producto
            SET stock = stock + ?
            WHERE id = ?
            """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, cantidad);
            statement.setInt(2, productoId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al aumentar stock."
            );

            e.printStackTrace();

            return false;
        }
    }

    // DISMINUIR STOCK
    public boolean disminuirStock(
            Connection conexion,
            int productoId,
            int cantidad) {

        String sql = """
            UPDATE producto
            SET stock = stock - ?
            WHERE id = ?
              AND stock >= ?
            """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, cantidad);
            statement.setInt(2, productoId);
            statement.setInt(3, cantidad);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al disminuir stock."
            );

            e.printStackTrace();

            return false;
        }
    }
}
