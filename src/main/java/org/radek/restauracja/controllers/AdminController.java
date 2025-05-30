package org.radek.restauracja.controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminController {
    public void logout(MouseEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene("main-window.fxml");
    }
}
