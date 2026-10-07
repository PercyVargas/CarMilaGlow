package org.example.carmilaglow.controller;

import org.example.carmilaglow.dao.UsuarioDAO;
import org.example.carmilaglow.model.Usuario;
import org.example.carmilaglow.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    @FXML
    private void iniciarSesion() {

        String nombreUsuario =
                txtUsuario.getText().trim();

        String password =
                txtPassword.getText();

        if (nombreUsuario.isBlank() ||
                password.isBlank()) {

            lblMensaje.setText(
                    "Completa usuario y contraseña."
            );

            return;
        }

        Usuario usuario =
                usuarioDAO.buscarPorUsuario(
                        nombreUsuario
                );

        if (usuario == null ||
                !usuario.getPassword()
                        .equals(password)) {


            lblMensaje.setText(
                    "Usuario o contraseña incorrectos."
            );

            txtPassword.clear();

            return;
        }

        Sesion.iniciarSesion(usuario);

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/menu.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    (Stage) txtUsuario
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "CarMila Glow"
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            lblMensaje.setText(
                    "No se pudo abrir el menú principal."
            );
        }
    }
}
