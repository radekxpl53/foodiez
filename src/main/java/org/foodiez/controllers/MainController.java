package org.foodiez.controllers;

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
import javafx.stage.Stage;
import org.foodiez.models.*;
import org.foodiez.exceptions.EmptyFieldException;
import org.foodiez.exceptions.WrongPasswordException;
import org.foodiez.util.Database;
import org.foodiez.util.SceneController;
import org.foodiez.util.Security;
import org.hibernate.query.Query;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        errorLabel.setVisible(false);
    }

    public void userLogin(ActionEvent actionEvent) {
        String login = "", password = "";
        try {
            login = loginField.getText();
            password = passwordField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldException("login lub hasło");
            }

            Query employeeQuery = Database.getSession().createQuery("FROM employees WHERE login = :login");
            employeeQuery.setParameter("login", login);

            try {
                Object pracownik = employeeQuery.getSingleResult();
                String dbPassword = ((Employee) pracownik).getPassword();
                String role = ((Employee) pracownik).getRole();

                if (Security.checkPasswd(password, dbPassword)) throw new WrongPasswordException();

                CurrentUser.setEmployee((Employee) pracownik);
                SceneController sceneController = new SceneController();
                sceneController.switchScene("admin-panel.fxml");

                return;
            } catch (NoResultException ignored) {}

            Query customerQuery = Database.getSession().createQuery("FROM customers WHERE login = :login");
            customerQuery.setParameter("login", login);

            Object customer = customerQuery.getSingleResult();
            String dbPassword = ((Customer) customer).getPassword();

            if (Security.checkPasswd(password, dbPassword)) throw new WrongPasswordException();

            CurrentUser.setCustomer((Customer) customer);
            SceneController sceneController = new SceneController();
            sceneController.switchScene("user-panel.fxml");

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

    public void startUserRegister(MouseEvent mouseEvent)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/org/foodiez/register-panel.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Foodiez! - Rejestracja");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}
