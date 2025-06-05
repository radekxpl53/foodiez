package org.foodiez.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.foodiez.util.Database;
import org.foodiez.models.Customer;
import org.foodiez.util.Email;
import org.foodiez.util.PhoneNumber;
import org.foodiez.util.Security;
import org.foodiez.exceptions.EmptyFieldException;
import org.foodiez.exceptions.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private final int PASSWORD_LENGTH = 6;

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

            if (passwordField.getText().length() >= PASSWORD_LENGTH) {
                customer.setPassword(Security.hashPasswd(getFieldValue(passwordField, "hasło")));
            } else throw new PasswordLengthException(PASSWORD_LENGTH);


            customer.setName(getFieldValue(nameField, "imie"));
            customer.setSurname( getFieldValue(surnameField, "nazwisko"));

            if (Email.isValid(getFieldValue(emailField, "email"))) {
                customer.setEmail(getFieldValue(emailField, "email"));
            } else throw new WrongEmailException();

            String phone = getFieldValue(phoneField, "telefon");
            if (PhoneNumber.isValid(phone)) {
                customer.setPhone(phoneField.getText());
            } else throw new WrongPhoneException();

            customer.setAddress(getFieldValue(addressField, "adres"));

            Database.addToDatabase(customer);
            errorLabel.setVisible(false);
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
