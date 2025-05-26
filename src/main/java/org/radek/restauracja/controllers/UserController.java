package org.radek.restauracja.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.radek.restauracja.classes.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private ListView<Danie> menuList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDaniaToList();
    }

    public void setDaniaToList() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        menuList.getItems().clear();

        List<Danie> result = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        menuList.getItems().addAll(FXCollections.observableList(result));
    }

    public void order(ActionEvent actionEvent) {
        Danie selectedDanie = menuList.getSelectionModel().getSelectedItem();

        if (selectedDanie != null) {
            Zamowienie zamowienie = new Zamowienie();
            zamowienie.setDanie(selectedDanie);
            zamowienie.setKlient(CurrentKlient.getKlient());

            Database.addToDatabase(zamowienie);
            InfoAlert.orderedAlert(selectedDanie);
        }
    }
}
