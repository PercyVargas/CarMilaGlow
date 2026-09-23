package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.database.DatabaseInitializer;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        DatabaseInitializer.crearTablas();

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("CarMila Glow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}