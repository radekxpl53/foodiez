package org.radek.restauracja.controllers;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import org.radek.restauracja.classes.Danie;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.InfoAlert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DanieEditController implements Initializable {
    public Button unselectBtn;
    @FXML
    private TableView<Danie> daniaTable;
    @FXML
    private TableColumn<Danie, String> nazwaCol;
    @FXML
    private TableColumn<Danie, String> opisCol;
    @FXML
    private TableColumn<Danie, Double> cenaCol;
    @FXML
    private TableColumn<Danie, String> kategoriaCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField nazwaField;
    @FXML
    private TextArea opisArea;
    @FXML
    private TextField cenaField;
    @FXML
    private ChoiceBox<String> kategoriaChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);

        unselectBtn.setOnAction((ActionEvent e) -> clearSelectedItem());

        //inicjalizacja kolumn
        nazwaCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("nazwa"));
        opisCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("opis"));
        cenaCol.setCellValueFactory(new PropertyValueFactory<Danie, Double>("cena"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("kategoria"));

        kategoriaChoice.getItems().addAll(Danie.kategorie);

        setDaniaToTable();
    }

    public void setDaniaToTable() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        daniaTable.getItems().clear();

        List<Danie> result = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        daniaTable.getItems().addAll(FXCollections.observableList(result));
    }

    public void addDanie(ActionEvent actionEvent) {
        try {
            Danie danie = new Danie();
            danie.setNazwa(nazwaField.getText());
            danie.setOpis(opisArea.getText());
            danie.setCena(Double.parseDouble(cenaField.getText()));
            danie.setKategoria(kategoriaChoice.getValue());

            Database.addToDatabase(danie);

            setDaniaToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        }
    }

    public void editDanie(ActionEvent actionEvent) {
        try {
            Danie danie = new Danie();
            danie.setId(Integer.parseInt(idField.getText()));
            danie.setNazwa(nazwaField.getText());
            danie.setOpis(opisArea.getText());
            danie.setCena(Double.parseDouble(cenaField.getText()));
            danie.setKategoria(kategoriaChoice.getValue());

            Database.editItemDatabase(danie);

            setDaniaToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        }
    }

    public void removeDanie(ActionEvent actionEvent) {
        try {
            Danie selectedDanie = daniaTable.getSelectionModel().getSelectedItem();

            Database.removeFromDatabase(selectedDanie);

            setDaniaToTable();
            clearSelectedItem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectItemToEdit(MouseEvent mouseEvent) {
        Danie selectedDanie = daniaTable.getSelectionModel().getSelectedItem();

        idField.setText(String.valueOf(selectedDanie.getId()));
        nazwaField.setText(selectedDanie.getNazwa());
        opisArea.setText(selectedDanie.getOpis());
        cenaField.setText(String.valueOf(selectedDanie.getCena()));
        kategoriaChoice.setValue(selectedDanie.getKategoria());
    }

    public void clearSelectedItem() {
        idField.setText("");
        nazwaField.setText("");
        opisArea.setText("");
        cenaField.setText("");
        kategoriaChoice.setValue("");

        daniaTable.getSelectionModel().clearSelection();
    }

}