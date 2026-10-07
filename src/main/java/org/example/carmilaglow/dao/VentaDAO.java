package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public int insertar(Venta venta) {

        String sql = """
                INSERT INTO venta
                (cliente_id, fecha, total, observacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, venta.getClienteId());
            statement.setString(2, venta.getFecha());
            statement.setInt(3, venta.getTotal());
            statement.setString(4, venta.getObservacion());

            statement.executeUpdate();

            try (ResultSet resultado =
                         statement.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar venta."
            );

            e.printStackTrace();
        }

        return -1;
    }

    public int insertar(
            Connection conexion,
            Venta venta) {

        String sql = """
                INSERT INTO venta
                (cliente_id, fecha, total, observacion)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, venta.getClienteId());
            statement.setString(2, venta.getFecha());
            statement.setInt(3, venta.getTotal());
            statement.setString(4, venta.getObservacion());

            statement.executeUpdate();

            try (ResultSet resultado =
                         statement.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar venta."
            );

            e.printStackTrace();
        }

        return -1;
    }

    public List<Venta> listar() {

        List<Venta> ventas =
                new ArrayList<>();

        String sql = """
                SELECT id, cliente_id,
                       fecha, total, observacion
                FROM venta
                ORDER BY id DESC
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Venta venta =
                        new Venta(
                                resultado.getInt("id"),
                                resultado.getInt("cliente_id"),
                                resultado.getString("fecha"),
                                resultado.getInt("total"),
                                resultado.getString("observacion")
                        );

                ventas.add(venta);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar ventas."
            );

            e.printStackTrace();
        }

        return ventas;
    }

    public boolean actualizar(Venta venta) {

        String sql = """
                UPDATE venta
                SET cliente_id = ?,
                    fecha = ?,
                    total = ?,
                    observacion = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, venta.getClienteId());
            statement.setString(2, venta.getFecha());
            statement.setInt(3, venta.getTotal());
            statement.setString(4, venta.getObservacion());
            statement.setInt(5, venta.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al actualizar venta."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM venta WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al eliminar venta."
            );

            e.printStackTrace();

            return false;
        }
    }
}
