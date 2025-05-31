package org.radek.restauracja.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.radek.restauracja.classes.CurrentUser;
import org.radek.restauracja.classes.Danie;
import org.radek.restauracja.classes.Database;
import org.radek.restauracja.classes.Zamowienie;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemController {

    @FXML
    private Label cenaLabel;

    @FXML
    private Label nazwaLabel;

    private Danie danie;

    public void setData(Danie danie) {
        this.danie = danie;
        nazwaLabel.setText(danie.getNazwa());
        cenaLabel.setText(danie.getCena() + "zł");
    }

    public void orderDanie(MouseEvent mouseEvent) {
        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setDanie(danie);
        zamowienie.setKlient(CurrentUser.getKlient());
        zamowienie.setDataZamowienia(Timestamp.valueOf(LocalDateTime.now()));

        Database.addToDatabase(zamowienie);

        System.out.println(zamowienie.getDataZamowienia());
    }

}
