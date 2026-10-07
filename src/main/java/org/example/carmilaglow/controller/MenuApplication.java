package org.example.carmilaglow.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.database.DatabaseInitializer;

import java.io.IOException;

public class MenuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseInitializer.crearTablaProducto();
        DatabaseInitializer.crearTablaCategoria();
        DatabaseInitializer.crearTablaProveedor();
        DatabaseInitializer.crearTablaMovimiento();
        DatabaseInitializer.crearTablaUsuario();

        DatabaseInitializer.crearTablaCliente();

        DatabaseInitializer.crearTablaCompra();
        DatabaseInitializer.crearTablaDetalleCompra();

        DatabaseInitializer.crearTablaVenta();
        DatabaseInitializer.crearTablaDetalleVenta();

        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CarMila Glow");
        stage.setScene(scene);
        stage.show();
    }
}
