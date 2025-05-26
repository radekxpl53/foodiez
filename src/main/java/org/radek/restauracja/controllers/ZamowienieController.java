package org.radek.restauracja.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.radek.restauracja.classes.Danie;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.Klient;
import org.radek.restauracja.classes.Zamowienie;
import org.radek.restauracja.util.DateFormatterUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ZamowienieController implements Initializable {

    @FXML
    private TableView<Zamowienie> zamowieniaTable;
    @FXML
    private TableColumn<Zamowienie, Integer> idCol;
    @FXML
    private TableColumn<Zamowienie, String> klientCol;
    @FXML
    private TableColumn<Zamowienie, String> danieCol;
    @FXML
    private TableColumn<Zamowienie, String> statusCol;
    @FXML
    private TableColumn<Zamowienie, String> dataCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<Zamowienie, Integer>("id"));
        klientCol.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("klient"));
        danieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDanie().getNazwa() + " " + cellData.getValue().getDanie().getCena() + "zł."));
        statusCol.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("status"));
        dataCol.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getDataZamowienia())));

        setZamowieniaToTable();
    }

    public void setZamowieniaToTable() {
        zamowieniaTable.getItems().clear();
        Danie danie = new Danie();

        List<Zamowienie> result = Database.getSession().createQuery("FROM zamowienie", Zamowienie.class).getResultList();
        zamowieniaTable.getItems().addAll(FXCollections.observableList(result));
    }
}
