package org.radek.restauracja.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.InfoAlert;
import org.radek.restauracja.classes.Pracownik;
import org.radek.restauracja.classes.Security;
import org.radek.restauracja.exceptions.EmptyFieldException;
import org.radek.restauracja.exceptions.WrongEmailException;

public class SetUpPracownikController implements Initializable {

    private Stage currentStage;

    private Pracownik tempPracownik = new Pracownik();

    @FXML private Label pracownikInfoLabel, errorLabel;
    @FXML private TextField loginField;
    @FXML private PasswordField hasloField;

    public void setTempPracownik(Pracownik pracownik) {
        tempPracownik = pracownik;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
        pracownikInfoLabel.setText("Login i hasło");
    }

    public void setLoginPassword(ActionEvent actionEvent) {
        String login = "", password = "";
        try {
            login = loginField.getText();
            password = hasloField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldException("login lub hasło");
            }

            Pracownik pracownik = Database.getSession().get(Pracownik.class, tempPracownik.getId());

            if (pracownik != null) {
                pracownik.setLogin(loginField.getText());
                pracownik.setHaslo(Security.hashPasswd(hasloField.getText()));
                Database.editItemDatabase(pracownik);
                currentStage = (Stage) loginField.getScene().getWindow();
                currentStage.close();
            }
        } catch (EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
