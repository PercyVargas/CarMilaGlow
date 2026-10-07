package org.example.carmilaglow.dao;

import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean insertar(Categoria categoria) {

        String sql = """
                INSERT INTO categoria
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, categoria.getNombre());
            statement.setString(2, categoria.getDescripcion());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar categoría.");
            e.printStackTrace();
            return false;
        }
    }

    public List<Categoria> listar() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = """
                SELECT id, nombre, descripcion
                FROM categoria
                ORDER BY nombre
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Categoria categoria = new Categoria(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("descripcion")
                );

                categorias.add(categoria);
            }

        } catch (Exception e) {
            System.out.println("Error al listar categorías.");
            e.printStackTrace();
        }

        return categorias;
    }

}
