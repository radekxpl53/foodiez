package org.radek.restauracja.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.radek.restauracja.classes.Danie;

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

}
