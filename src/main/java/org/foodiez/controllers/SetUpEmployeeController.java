package org.foodiez.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.foodiez.classes.Database;
import org.foodiez.classes.Employee;
import org.foodiez.classes.Security;
import org.foodiez.exceptions.EmptyFieldException;

public class SetUpEmployeeController implements Initializable {

    private Stage currentStage;

    private Employee tempEmployee = new Employee();

    @FXML private Label employeeInfoLabel, errorLabel;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;

    public void setTempPracownik(Employee employee) {
        tempEmployee = employee;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
        employeeInfoLabel.setText("Login i hasło");
    }

    public void setLoginPassword(ActionEvent actionEvent) {
        String login = "", password = "";
        try {
            login = loginField.getText();
            password = passwordField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldException("login lub hasło");
            }

            Employee employee = Database.getSession().get(Employee.class, tempEmployee.getId());

            if (employee != null) {
                employee.setLogin(loginField.getText());
                employee.setPassword(Security.hashPasswd(passwordField.getText()));
                Database.editItemDatabase(employee);
                currentStage = (Stage) loginField.getScene().getWindow();
                currentStage.close();
            }
        } catch (EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
