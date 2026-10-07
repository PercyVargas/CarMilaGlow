package org.example.carmilaglow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.database.DatabaseInitializer;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

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

        DatabaseInitializer.cambiarCategoria(1, "ACCESORIO");

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/org/example/carmilaglow/login.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Gentión de Ventas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
