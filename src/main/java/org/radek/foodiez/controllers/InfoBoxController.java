package org.radek.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InfoBoxController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private Button button;

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContent(String content) {
        contentLabel.setText(content);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) contentLabel.getScene().getWindow();
        stage.close();
    }

}
