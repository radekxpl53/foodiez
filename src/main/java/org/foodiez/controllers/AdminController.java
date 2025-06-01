package org.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import org.foodiez.classes.CurrentUser;
import org.foodiez.classes.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    public Tab employeesTab;

    @FXML private Label currentUserLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Employee emp = CurrentUser.getEmployee();
        if (emp != null) {
            if (!emp.getRole().equals("admin")) {
                employeesTab.setDisable(true);
            }

            currentUserLabel.setText("zalogowano jako: " + CurrentUser.getEmployee().getLogin());
        }

    }

    public void logout(MouseEvent actionEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene("main-window.fxml");
    }

}
