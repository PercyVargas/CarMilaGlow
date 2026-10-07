package org.example.carmilaglow.controller;

import org.example.carmilaglow.Sesion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;


public class MenuController {

    @FXML
    private Button btnUsuarios;

    @FXML
    private Label lblUsuario;

    @FXML
    public void initialize() {

        if (Sesion.getUsuarioActual() != null) {

            String nombre =
                    Sesion.getUsuarioActual()
                            .getNombreUsuario();

            String rol =
                    Sesion.getUsuarioActual()
                            .getRol();

            lblUsuario.setText(
                    "Usuario: " + nombre +
                            " | Rol: " + rol
            );

            if (Sesion.esEmpleado()) {
                btnUsuarios.setVisible(false);
                btnUsuarios.setManaged(false);
            }
        }
    }

    private void abrirVista(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(rutaFXML));

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblUsuario.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LLAMADO DE ABRIR PRODUCTOS NORMAL
    @FXML
    private void abrirProductos() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/org/example/carmilaglow/producto.fxml"
                            ));

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblUsuario.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Productos");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LLAMADO DE ABRIR PROVEEDOR A TRAVEZ DE UNA FUNCIÓN AbrirVista
    @FXML
    private void abrirProveedores() {
        abrirVista(
                "/org/example/carmilaglow/proveedor.fxml",
                "Gestión de Proveedores"
        );
    }

    @FXML
    private void abrirClientes() {
        abrirVista(
                "/org/example/carmilaglow/cliente.fxml",
                "Gestión de Clientes"
        );
    }

    @FXML
    private void abrirCompras() {
        abrirVista(
                "/org/example/carmilaglow/compra.fxml",
                "Gestión de Compras"
        );
    }

    @FXML
    private void abrirVentas() {
        abrirVista(
                "/org/example/carmilaglow/venta.fxml",
                "Gestión de Ventas"
        );
    }

    @FXML
    private void abrirMovimientos() {
        abrirVista(
                "/org/example/carmilaglow/movimiento.fxml",
                "Gestión de Movimiento"
        );
    }

    @FXML
    private void abrirUsuarios() {
        abrirVista(
                "/org/example/carmilaglow/usuario.fxml",
                "Gestión de Usuario"
        );
    }

    @FXML
    private void abrirCategorias() {
        abrirVista(
                "/org/example/carmilaglow/producto.fxml",
                "Categoría"
        );
    }

    @FXML
    private void cerrarSesion() {

        Sesion.cerrarSesion();

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/org/example/carmilaglow/login.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblUsuario
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "Inicio de Sesión"
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
