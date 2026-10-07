package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.DetalleVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {

    public boolean insertar(
            DetalleVenta detalle) {

        String sql = """
                INSERT INTO detalle_venta
                (venta_id, producto_id, cantidad,
                 precio_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getVentaId());
            statement.setInt(2, detalle.getProductoId());
            statement.setInt(3, detalle.getCantidad());
            statement.setInt(4, detalle.getPrecioUnitario());
            statement.setInt(5, detalle.getSubtotal());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar detalle de venta.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertar(
            Connection conexion,
            DetalleVenta detalle) {

        String sql = """
                INSERT INTO detalle_venta
                (venta_id, producto_id, cantidad,
                 precio_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getVentaId());
            statement.setInt(2, detalle.getProductoId());
            statement.setInt(3, detalle.getCantidad());
            statement.setInt(4, detalle.getPrecioUnitario());
            statement.setInt(5, detalle.getSubtotal());

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar detalle de venta.");
            e.printStackTrace();
            return false;
        }
    }

    public List<DetalleVenta> listarPorVenta(int ventaId) {

        List<DetalleVenta> detalles = new ArrayList<>();

        String sql = """
                SELECT id, venta_id, producto_id,
                       cantidad, precio_unitario, subtotal
                FROM detalle_venta
                WHERE venta_id = ?
                ORDER BY id
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, ventaId);

            try (ResultSet resultado =    statement.executeQuery()) {

                while (resultado.next()) {
                    DetalleVenta detalle =
                            new DetalleVenta(
                                    resultado.getInt("id"),
                                    resultado.getInt("venta_id"),
                                    resultado.getInt("producto_id"),
                                    resultado.getInt("cantidad"),
                                    resultado.getInt("precio_unitario"),
                                    resultado.getInt("subtotal")
                            );
                    detalles.add(detalle);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al listar detalles de venta.");
            e.printStackTrace();
        }

        return detalles;
    }

    public boolean actualizar(
            DetalleVenta detalle) {

        String sql = """
                UPDATE detalle_venta
                SET producto_id = ?,
                    cantidad = ?,
                    precio_unitario = ?,
                    subtotal = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getProductoId());
            statement.setInt(2, detalle.getCantidad());
            statement.setInt(3, detalle.getPrecioUnitario());
            statement.setInt(4, detalle.getSubtotal());
            statement.setInt(5, detalle.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar detalle de venta.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM detalle_venta WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al eliminar detalle de venta.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPorVenta(
            int ventaId) {

        String sql =
                "DELETE FROM detalle_venta WHERE venta_id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, ventaId);

            return statement.executeUpdate() >= 0;

        } catch (Exception e) {

            System.out.println("Error al eliminar detalles de la venta.");
            e.printStackTrace();
            return false;
        }
    }
}
