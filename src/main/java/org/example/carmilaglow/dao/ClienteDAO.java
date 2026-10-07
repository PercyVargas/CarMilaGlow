package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {

    public boolean insertar(Cliente cliente) {

        String sql = """
                INSERT INTO cliente
                (nombre, documento, telefono, email, direccion)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDocumento());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.setString(5, cliente.getDireccion());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al insertar cliente.");
            e.printStackTrace();

            return false;
        }
    }


    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = """
                SELECT id, nombre, documento,
                       telefono, email, direccion
                FROM cliente
                ORDER BY nombre
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Cliente cliente = new Cliente(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("documento"),
                        resultado.getString("telefono"),
                        resultado.getString("email"),
                        resultado.getString("direccion")
                );

                clientes.add(cliente);
            }

        } catch (Exception e) {

            System.out.println("Error al listar clientes.");
            e.printStackTrace();
        }

        return clientes;
    }


    public boolean actualizar(Cliente cliente) {

        String sql = """
                UPDATE cliente
                SET nombre = ?,
                    documento = ?,
                    telefono = ?,
                    email = ?,
                    direccion = ?
                WHERE id = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDocumento());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.setString(5, cliente.getDireccion());
            statement.setInt(6, cliente.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al actualizar cliente.");
            e.printStackTrace();

            return false;
        }
    }


    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM cliente WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error al eliminar cliente.");
            e.printStackTrace();

            return false;
        }
    }
}
