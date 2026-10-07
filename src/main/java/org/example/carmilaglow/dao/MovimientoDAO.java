package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MovimientoDAO {

    public boolean insertar(
            Connection conexion,
            int productoId,
            String tipo,
            int cantidad,
            String fecha,
            String observacion) {

        String sql = """
                INSERT INTO movimiento
                (producto_id, tipo, cantidad, fecha, observacion)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, productoId);
            statement.setString(2, tipo);
            statement.setInt(3, cantidad);
            statement.setString(4, fecha);
            statement.setString(5, observacion);

            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar movimiento.");
            e.printStackTrace();
            return false;
        }
    }

    public List<Movimiento> listar() {

        List<Movimiento> movimientos =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       producto_id,
                       tipo,
                       cantidad,
                       fecha,
                       observacion
                FROM movimiento
                ORDER BY id DESC
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Movimiento movimiento =
                        new Movimiento(
                                resultado.getInt("id"),
                                resultado.getInt("producto_id"),
                                resultado.getString("tipo"),
                                resultado.getInt("cantidad"),
                                resultado.getString("fecha"),
                                resultado.getString("observacion")
                        );
                movimientos.add(movimiento);
            }
        } catch (Exception e) {

            System.out.println("Error al listar movimientos.");
            e.printStackTrace();
        }
        return movimientos;
    }

    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM movimiento WHERE id = ?";

        try (Connection conexion =
                     Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al eliminar movimiento."
            );
            e.printStackTrace();
            return false;
        }
    }
}
