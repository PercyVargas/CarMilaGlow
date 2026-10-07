package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.DetalleCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DetalleCompraDAO {

    public boolean insertar(DetalleCompra detalle) {

        String sql = """
                INSERT INTO detalle_compra
                (compra_id, producto_id, cantidad,
                 precio_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getCompraId());
            statement.setInt(2, detalle.getProductoId());
            statement.setInt(3, detalle.getCantidad());
            statement.setInt(4, detalle.getPrecioUnitario());
            statement.setInt(5, detalle.getSubtotal());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar detalle de compra."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean insertar(
            Connection conexion,
            DetalleCompra detalle) {

        String sql = """
            INSERT INTO detalle_compra
            (compra_id, producto_id, cantidad,
             precio_unitario, subtotal)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getCompraId());
            statement.setInt(2, detalle.getProductoId());
            statement.setInt(3, detalle.getCantidad());
            statement.setInt(4, detalle.getPrecioUnitario());
            statement.setInt(5, detalle.getSubtotal());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar detalle de compra."
            );

            e.printStackTrace();

            return false;
        }
    }

    public List<DetalleCompra> listarPorCompra(int compraId) {

        List<DetalleCompra> detalles =
                new ArrayList<>();

        String sql = """
                SELECT id, compra_id, producto_id,
                       cantidad, precio_unitario, subtotal
                FROM detalle_compra
                WHERE compra_id = ?
                ORDER BY id
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, compraId);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                while (resultado.next()) {

                    DetalleCompra detalle =
                            new DetalleCompra(
                                    resultado.getInt("id"),
                                    resultado.getInt("compra_id"),
                                    resultado.getInt("producto_id"),
                                    resultado.getInt("cantidad"),
                                    resultado.getInt("precio_unitario"),
                                    resultado.getInt("subtotal")
                            );

                    detalles.add(detalle);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar detalles de compra."
            );

            e.printStackTrace();
        }

        return detalles;
    }

    public boolean actualizar(DetalleCompra detalle) {

        String sql = """
                UPDATE detalle_compra
                SET producto_id = ?,
                    cantidad = ?,
                    precio_unitario = ?,
                    subtotal = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, detalle.getProductoId());
            statement.setInt(2, detalle.getCantidad());
            statement.setInt(3, detalle.getPrecioUnitario());
            statement.setInt(4, detalle.getSubtotal());
            statement.setInt(5, detalle.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al actualizar detalle de compra."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM detalle_compra WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al eliminar detalle de compra."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean eliminarPorCompra(int compraId) {

        String sql =
                "DELETE FROM detalle_compra WHERE compra_id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, compraId);

            return statement.executeUpdate() >= 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al eliminar detalles de la compra."
            );

            e.printStackTrace();

            return false;
        }
    }
}
