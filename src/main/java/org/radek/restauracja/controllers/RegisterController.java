package org.radek.restauracja.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.Klient;
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


    public void userRegister(ActionEvent actionEvent) {
        Klient klient = new Klient();
        String imie = "", nazwisko = "", email = "", adres = "", telefon = "", login = "", haslo = "";
        try {
            login = loginField.getText(); if (login.isEmpty()) throw new EmptyFieldException("login"); klient.setLogin(login);
            haslo = hasloField.getText(); if (haslo.isEmpty()) throw new EmptyFieldException("haslo"); klient.setHaslo(haslo);
            imie = imieField.getText(); if (imie.isEmpty()) throw new EmptyFieldException("imie"); klient.setImie(imie);
            nazwisko = nazwiskoField.getText(); if (nazwisko.isEmpty()) throw new EmptyFieldException("nazwisko"); klient.setNazwisko(nazwisko);
            email = emailField.getText(); if (email.isEmpty()) throw new EmptyFieldException("email"); klient.setEmail(email);
            telefon = telField.getText(); if (telefon.isEmpty()) throw new EmptyFieldException("telefon"); klient.setTelefon(telefon);
            adres = adresField.getText(); if (adres.isEmpty()) throw new EmptyFieldException("adres"); klient.setAdres(adres);

            Database.addToDatabase(klient);
            errorLabel.setVisible(false);
        } catch (EmptyFieldException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
    }

}
