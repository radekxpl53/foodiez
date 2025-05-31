package org.radek.restauracja.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.Klient;
import org.radek.restauracja.classes.Security;
import org.radek.restauracja.exceptions.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField hasloField;
    @FXML
    private TextField imieField;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telField;
    @FXML
    private TextField adresField;

    private String getFieldValue(TextField field, String fieldName) throws EmptyFieldException {
        String value = field.getText();
        if (value == null || value.trim().isEmpty()) {
            throw new EmptyFieldException(fieldName);
        }
        return value.trim();
    }


    public void userRegister(ActionEvent actionEvent) {
        try {
            Klient klient = new Klient();
            klient.setLogin(getFieldValue(loginField, "login"));
            klient.setHaslo(Security.hashPasswd(getFieldValue(hasloField, "haslo")));
            klient.setImie(getFieldValue(imieField, "imie"));
            klient.setNazwisko(getFieldValue(nazwiskoField, "nazwisko"));
            klient.setEmail(getFieldValue(emailField, "email"));
            klient.setTelefon(getFieldValue(telField, "telefon"));
            klient.setAdres(getFieldValue(adresField, "adres"));

            Database.addToDatabase(klient);
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
