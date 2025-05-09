package org.radek.restauracja.controllers;

import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.radek.restauracja.*;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.InfoAlert;
import org.radek.restauracja.classes.Pracownik;
import org.radek.restauracja.exceptions.EmptyStringException;
import org.radek.restauracja.exceptions.WrongPasswordException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.lang.*;

public class MainController implements Initializable {

    public Button loginBtn;
    @FXML
    private Label currentDateLabel;

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private final LocalDate date = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentDateLabel.setText(String.valueOf(date));
    }

    public void userLogin(ActionEvent actionEvent) {
        String login, password, fetchedPassword = "", fetchedRole = "";
        Pracownik pracownik = null;
        try {
            login = loginField.getText();
            password = passwordField.getText();
            if (login.isEmpty()) throw new EmptyStringException();
            fetchedPassword = Database.getSession().createQuery("SELECT haslo FROM pracownik WHERE login = '" + login +"'").getSingleResult().toString();
            System.out.println(fetchedPassword);
            fetchedRole = Database.getSession().createQuery("SELECT rola FROM pracownik WHERE login = '" + login + "'").getSingleResult().toString();
            if(fetchedPassword.equals(password)) {
                if (fetchedRole.equals("admin")) {
                    System.out.println(fetchedRole);
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin-pane.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    MainApplication.switchScene(scene);
                } else {
                    System.out.println(fetchedRole);
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-pane.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    MainApplication.switchScene(scene);
                }
            } else throw new WrongPasswordException();
        } catch (EmptyStringException e) {
            InfoAlert.emptyFieldAlert();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoResultException | WrongPasswordException e) {
            InfoAlert.noUserAlert();
        }

    }
}
