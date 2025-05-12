package org.radek.restauracja.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.validator.routines.EmailValidator;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.InfoAlert;
import org.radek.restauracja.classes.Pracownik;
import org.radek.restauracja.classes.Security;
import org.radek.restauracja.exceptions.EmptyFieldException;
import org.radek.restauracja.exceptions.WrongEmailException;
import org.radek.restauracja.exceptions.WrongPasswordException;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PracownikEditController implements Initializable {

    @FXML
    private TableView<Pracownik> pracownicyTable;
    @FXML
    private TableColumn<Pracownik, Integer> idCol;
    @FXML
    private TableColumn<Pracownik, String> imieCol;
    @FXML
    private TableColumn<Pracownik, String> nazwiskoCol;
    @FXML
    private TableColumn<Pracownik, String> emailCol;
    @FXML
    private TableColumn<Pracownik, String> telCol;
    @FXML
    private TableColumn<Pracownik, String> rolaCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField imieField;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telField;
    @FXML
    private ChoiceBox<String> rolaChoice;
    @FXML
    private Button unselectBtn;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);

        errorLabel.setVisible(false);

        unselectBtn.setOnAction((ActionEvent e) -> clearSelectedItem());

        //inicjalizacja kolumn
        idCol.setCellValueFactory(new PropertyValueFactory<Pracownik, Integer>("id"));
        imieCol.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("imie"));
        nazwiskoCol.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("nazwisko"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("email"));
        telCol.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("telefon"));
        rolaCol.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("rola"));

        rolaChoice.getItems().setAll(Pracownik.role);

        setPracownicyToTable();
    }

    public void setPracownicyToTable() {
        //pobranie pracowników z bazy danych i przypisanie do TableView;
        pracownicyTable.getItems().clear();

        List<Pracownik> result = Database.getSession().createQuery("FROM pracownik WHERE rola != 'admin'", Pracownik.class).getResultList();
        pracownicyTable.getItems().addAll(FXCollections.observableList(result));
    }

    public void clearSelectedItem() {
        idField.setText("");
        imieField.setText("");
        nazwiskoField.setText("");
        emailField.setText("");
        telField.setText("");
        rolaChoice.setValue("");

        pracownicyTable.getSelectionModel().clearSelection();
    }

    public void addPracownik(ActionEvent actionEvent) {
        try {
            Pracownik pracownik = new Pracownik();
            pracownik.setImie(imieField.getText());
            pracownik.setNazwisko(nazwiskoField.getText());
            if (EmailValidator.getInstance().isValid(emailField.getText())) {
                pracownik.setEmail(emailField.getText());
            } else throw new WrongEmailException();
            pracownik.setTelefon(telField.getText());
            pracownik.setRola(rolaChoice.getValue());

            Database.addToDatabase(pracownik);

            setPracownicyToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        } catch (WrongEmailException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    public void setLoginAndPasswd(ActionEvent actionEvent) {
        if (!pracownicyTable.getSelectionModel().isEmpty()) {
            Pracownik selectedPracownik = pracownicyTable.getSelectionModel().getSelectedItem();

            TextInputDialog loginInputDialog = new TextInputDialog();
            loginInputDialog.setTitle("Login dla " + selectedPracownik.getImie() + " " + selectedPracownik.getNazwisko());
            loginInputDialog.setHeaderText("Wprowadź login");
            loginInputDialog.setContentText("login:");

            Optional<String> resultLogin = loginInputDialog.showAndWait();

            if (resultLogin.isPresent()) {
                TextInputDialog passwordInputDialog = new TextInputDialog();
                passwordInputDialog.setTitle("Hasło dla " + selectedPracownik.getImie() + " " + selectedPracownik.getNazwisko());
                passwordInputDialog.setHeaderText("Wprowadź hasło");
                passwordInputDialog.setContentText("hasło:");

                Optional<String> resultPassword = passwordInputDialog.showAndWait();

                if (resultPassword.isPresent()) {
                    selectedPracownik.setLogin(resultLogin.get());
                    selectedPracownik.setHaslo(Security.hashPasswd(resultPassword.get()));

                    Database.editItemDatabase(selectedPracownik);
                } else {
                    InfoAlert.emptySelectionAlert();
                }
            }
        } else {
            InfoAlert.emptySelectionAlert();
        }

    }
}
