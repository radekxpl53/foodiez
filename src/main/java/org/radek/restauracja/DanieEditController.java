package org.radek.restauracja;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DanieEditController implements Initializable {
    @FXML
    private TableView<Danie> daniaTable;
    @FXML
    private TableColumn<Danie, Integer> idCol;
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


    public void setDaniaToTable() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        daniaTable.getItems().clear();

        List<Danie> result = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        daniaTable.getItems().addAll(FXCollections.observableList(result));
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);


        //inicjalizacja kolumn
        idCol.setCellValueFactory(new PropertyValueFactory<Danie, Integer>("id"));
        nazwaCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("nazwa"));
        opisCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("opis"));
        cenaCol.setCellValueFactory(new PropertyValueFactory<Danie, Double>("cena"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<Danie, String>("kategoria"));

        kategoriaChoice.getItems().addAll(Danie.kategorie);

        setDaniaToTable();
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
        } catch (NullPointerException e) {
            System.out.println(e.getMessage() + "Pole nie może być puste!");
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
        } catch (NullPointerException e) {
            System.out.println(e.getMessage() + "Pole nie może być puste!");
        }
    }

    public void selectItemToEdit(MouseEvent mouseEvent) {
        Danie selectedDanie = daniaTable.getSelectionModel().getSelectedItem();

        idField.setText(selectedDanie.getId() + "");
        nazwaField.setText(selectedDanie.getNazwa());
        opisArea.setText(selectedDanie.getOpis());
        cenaField.setText(selectedDanie.getCena() + "");
        kategoriaChoice.setValue(selectedDanie.getKategoria());
    }

}