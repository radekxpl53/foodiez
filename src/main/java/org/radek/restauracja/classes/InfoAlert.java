package org.radek.restauracja.classes;

import javafx.scene.control.Alert;

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
}
