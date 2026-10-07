package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    public int insertar(Compra compra) {

        String sql = """
                INSERT INTO compra
                (proveedor_id, fecha, total, observacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, compra.getProveedorId());
            statement.setString(2, compra.getFecha());
            statement.setInt(3, compra.getTotal());
            statement.setString(4, compra.getObservacion());

            statement.executeUpdate();

            try (ResultSet resultado =
                    statement.getGeneratedKeys()) {
                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar compra."
            );
            e.printStackTrace();
        }
        return -1;
    }

    public int insertar(Connection conexion, Compra compra) {

        String sql = """
            INSERT INTO compra
            (proveedor_id, fecha, total, observacion)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, compra.getProveedorId());
            statement.setString(2, compra.getFecha());
            statement.setInt(3, compra.getTotal());
            statement.setString(4, compra.getObservacion());

            statement.executeUpdate();

            try (ResultSet resultado =
                         statement.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar compra."
            );

            e.printStackTrace();
        }

        return -1;
    }

    public List<Compra> listar() {

        List<Compra> compras = new ArrayList<>();

        String sql = """
                SELECT id, proveedor_id,
                       fecha, total, observacion
                FROM compra
                ORDER BY id DESC
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Compra compra = new Compra(
                        resultado.getInt("id"),
                        resultado.getInt("proveedor_id"),
                        resultado.getString("fecha"),
                        resultado.getInt("total"),
                        resultado.getString("observacion")
                );

                compras.add(compra);
            }

        } catch (Exception e) {

            System.out.println("Error al listar compras.");
            e.printStackTrace();
        }

        return compras;
    }

    public boolean actualizar(Compra compra) {

        String sql = """
                UPDATE compra
                SET proveedor_id = ?,
                    fecha = ?,
                    total = ?,
                    observacion = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, compra.getProveedorId());
            statement.setString(2, compra.getFecha());
            statement.setInt(3, compra.getTotal());
            statement.setString(4, compra.getObservacion());
            statement.setInt(5, compra.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al actualizar compra.");
            e.printStackTrace();

            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM compra WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al eliminar compra.");
            e.printStackTrace();

            return false;
        }
    }
}
