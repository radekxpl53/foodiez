package org.foodiez.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;
import org.foodiez.classes.Database;
import org.foodiez.classes.Customer;
import org.foodiez.classes.Email;
import org.foodiez.classes.Security;
import org.foodiez.exceptions.EmptyFieldException;
import org.foodiez.exceptions.*;

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
            customer.setPassword(Security.hashPasswd(getFieldValue(passwordField, "hasło")));
            customer.setName(getFieldValue(nameField, "imie"));
            customer.setSurname( getFieldValue(surnameField, "nazwisko"));

            if (Email.isValid(getFieldValue(emailField, "email"))) {
                customer.setEmail(getFieldValue(emailField, "email"));
            } else throw new WrongEmailException();

            String phone = getFieldValue(phoneField, "telefon");
            if ( phone.length() == 9 ) {
                customer.setPhone(getFieldValue(phoneField, "telefon"));
            } else throw new WrongPhoneException();

            customer.setAddress(getFieldValue(addressField, "adres"));

            Database.addToDatabase(customer);
            errorLabel.setVisible(false);

            Platform.exit();
        } catch (EmptyFieldException | WrongEmailException | WrongPhoneException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
    }

}
