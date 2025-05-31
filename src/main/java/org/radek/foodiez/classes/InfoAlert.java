package org.radek.foodiez.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.radek.foodiez.controllers.InfoBoxController;

import java.io.IOException;

public class InfoAlert {
    public static void emptyFieldAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setContentText("Pola nie mogą być puste!");
        alert.showAndWait();
    }

    public static void noUserAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setContentText("Błędny login lub hasło!");
        alert.showAndWait();
    }

    public static void emptySelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setContentText("Nie wybrano nic z listy!");
        alert.showAndWait();
    }

    public static void orderedAlert(Dish dish) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zamówiono!");
        alert.setContentText("Złożono zamówienie: " + dish.getName());
        alert.showAndWait();
    }

    public static void infoAlert(String title, String content) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InfoAlert.class.getResource("/org/radek/restauracja/info-box.fxml"));
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
