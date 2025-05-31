package org.radek.restauracja.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.query.Query;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.Zamowienie;
import org.radek.restauracja.util.DateFormatterUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ZamowienieController implements Initializable {

    @FXML
    private TableView<Zamowienie> zamowieniaTable;
    @FXML
    private TableColumn<Zamowienie, String> klientCol;
    @FXML
    private TableColumn<Zamowienie, String> danieCol;
    @FXML
    private TableColumn<Zamowienie, String> statusCol;
    @FXML
    private TableColumn<Zamowienie, String> dataCol;
    @FXML
    private RadioButton completedRadio;

    private Zamowienie selectedZamowienie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        klientCol.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("klient"));
        danieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDanie().getNazwa() + " " + cellData.getValue().getDanie().getCena() + "zł."));
        statusCol.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("status"));
        dataCol.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getDataZamowienia())));

        setZamowieniaToTable();
    }

    public void setZamowieniaToTable() {
        zamowieniaTable.getItems().clear();

        List<Zamowienie> result;

        if(completedRadio.isSelected()) {
            result = Database.getSession().createQuery("FROM zamowienie", Zamowienie.class).getResultList();
        } else {
            result = Database.getSession().createQuery("FROM zamowienie WHERE status='oczekujące'", Zamowienie.class).getResultList();
        }

        zamowieniaTable.getItems().addAll(FXCollections.observableList(result));
    }

    public void completeOrder(MouseEvent event) {
        Database.getSession().beginTransaction();

        Query zamowienieQuery = Database.getSession().createQuery("UPDATE zamowienie SET status=:status WHERE id=:id");
        zamowienieQuery.setParameter("status", "zrealizowane");
        zamowienieQuery.setParameter("id", selectedZamowienie.getId());
        zamowienieQuery.executeUpdate();

        Database.getSession().getTransaction().commit();

        setZamowieniaToTable();
    }

    public void selectItemToEdit(MouseEvent event) {
        selectedZamowienie = zamowieniaTable.getSelectionModel().getSelectedItem();
    }

    public void checkedFilter(ActionEvent action) {
        setZamowieniaToTable();
    }

    public void deleteOrder(MouseEvent event) {
        Database.removeFromDatabase(selectedZamowienie);
        setZamowieniaToTable();
    }

}
