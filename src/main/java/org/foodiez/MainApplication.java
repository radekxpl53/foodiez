package org.foodiez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.foodiez.util.Database;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());
        stage = primaryStage;
        stage.setTitle("Foodiez!");
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("pictures/foodiez-icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        Database.close();
    }

    public static void switchScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        Database.connect();
        launch();
    }
}