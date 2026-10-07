package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public boolean insertar(Proveedor proveedor) {

        String sql = """
                INSERT INTO proveedor
                (nombre, telefono, email, direccion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getTelefono());
            statement.setString(3, proveedor.getEmail());
            statement.setString(4, proveedor.getDireccion());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al insertar proveedor.");
            e.printStackTrace();

            return false;
        }
    }

    public List<Proveedor> listar() {

        List<Proveedor> proveedores = new ArrayList<>();

        String sql = """
                SELECT id, nombre, telefono, email, direccion
                FROM proveedor
                ORDER BY nombre
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Proveedor proveedor = new Proveedor(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("email"),
                        resultado.getString("direccion")
                );

                proveedores.add(proveedor);
            }

        } catch (Exception e) {

            System.out.println("Error al listar proveedores.");
            e.printStackTrace();
        }

        return proveedores;
    }

    public boolean actualizar(Proveedor proveedor) {

        String sql = """
                UPDATE proveedor
                SET nombre = ?,
                    telefono = ?,
                    email = ?,
                    direccion = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getTelefono());
            statement.setString(3, proveedor.getEmail());
            statement.setString(4, proveedor.getDireccion());
            statement.setInt(5, proveedor.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al actualizar proveedor.");
            e.printStackTrace();

            return false;
        }
    }

    public boolean eliminar(int id) {

        String sql = "DELETE FROM proveedor WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al eliminar proveedor.");
            e.printStackTrace();

            return false;
        }
    }
}
