package org.foodiez.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.foodiez.classes.Database;
import org.foodiez.classes.Customer;
import org.foodiez.classes.Security;
import org.foodiez.exceptions.EmptyFieldException;
import org.radek.foodiez.exceptions.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    private String getFieldValue(TextField field, String fieldName) throws EmptyFieldException {
        String value = field.getText();
        if (value == null || value.trim().isEmpty()) {
            throw new EmptyFieldException(fieldName);
        }
        return value.trim();
    }


    public void customerRegister(ActionEvent actionEvent) {
        try {
            Customer customer = new Customer();
            customer.setLogin(getFieldValue(loginField, "login"));
            customer.setPassword(Security.hashPasswd(getFieldValue(passwordField, "password")));
            customer.setName(getFieldValue(nameField, "name"));
            customer.setSurname(getFieldValue(surnameField, "surname"));
            customer.setEmail(getFieldValue(emailField, "email"));
            customer.setPhone(getFieldValue(phoneField, "phone"));
            customer.setAddress(getFieldValue(addressField, "address"));

            Database.addToDatabase(customer);
            errorLabel.setVisible(false);

            Platform.exit();
        } catch (EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
    }

}
