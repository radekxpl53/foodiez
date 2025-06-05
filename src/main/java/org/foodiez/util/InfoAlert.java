package org.foodiez.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.foodiez.controllers.InfoBoxController;

import java.io.IOException;

public class InfoAlert {
    public static void emptyFieldAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setContentText("Pola nie mogą być puste!");
        alert.showAndWait();
    }

    public static void infoAlert(String title, String content) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InfoAlert.class.getResource("/org/foodiez/info-box.fxml"));
        Parent root = fxmlLoader.load();
        InfoBoxController controller = fxmlLoader.getController();

        controller.setTitle(title);
        controller.setContent(content);

        Stage stage = new Stage();

        stage.setTitle("Info!");
        stage.initModality(Modality.NONE);
        stage.setScene(new Scene(root));

        stage.show();
    }
}
