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
import org.hibernate.query.Query;
import org.radek.restauracja.*;
import org.radek.restauracja.classes.Database;
import javafx.scene.input.MouseEvent;
import org.radek.restauracja.exceptions.*;
import org.radek.restauracja.classes.*;


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
    @FXML
    private Label errorLabel;

    private final LocalDate date = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentDateLabel.setText(String.valueOf(date));
        errorLabel.setVisible(false);
    }

    public void userLogin(ActionEvent actionEvent) throws IOException {
        String login = "", password = "";
        try {
            login = loginField.getText();
            password = passwordField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldException("login lub hasło");
            }

            Query pracownikQuery = Database.getSession().createQuery("FROM pracownik WHERE login = :login");
            pracownikQuery.setParameter("login", login);

            try {
                Object pracownik = pracownikQuery.getSingleResult();
                String dbPassword = ((Pracownik) pracownik).getHaslo();
                String role = ((Pracownik) pracownik).getRola();

                if (!dbPassword.equals(password)) throw new WrongPasswordException();

                if (role.equals("admin")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin-pane.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    MainApplication.switchScene(scene);
                }
                return;
            } catch (NoResultException ignored) {}

            Query klientQuery = Database.getSession().createQuery("FROM klient WHERE login = :login");
            klientQuery.setParameter("login", login);

            Object klient = klientQuery.getSingleResult();
            String dbPassword = ((Klient) klient).getHaslo();

            if (!dbPassword.equals(password)) throw new WrongPasswordException();

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-pane.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainApplication.switchScene(scene);

        } catch (EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        } catch (WrongPasswordException | NoResultException e) {
            errorLabel.setText("Zły login lub hasło!");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            errorLabel.setText("Błąd systemu.");
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }


    public void startUserRegister(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("register-pane.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.switchScene(scene);
    }
}
