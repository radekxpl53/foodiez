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
import org.foodiez.util.Database;
import org.foodiez.models.Employee;
import org.foodiez.util.Security;
import org.foodiez.exceptions.EmptyFieldException;
import org.foodiez.exceptions.PasswordLengthException;

public class SetUpEmployeeController implements Initializable {

    private final int PASSWORD_LENGTH = 6;

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

            if (passwordField.getText().length() < PASSWORD_LENGTH) throw new PasswordLengthException(PASSWORD_LENGTH);
            else password = passwordField.getText();

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

        } catch (EmptyFieldException | PasswordLengthException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
    }
}
