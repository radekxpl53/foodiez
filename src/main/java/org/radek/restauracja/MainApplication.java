package org.radek.restauracja;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.radek.restauracja.classes.Database;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());
        stage = primaryStage;
        stage.setTitle("System Zarządzania Restauracją");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        Database.connect();
        launch();
    }
}