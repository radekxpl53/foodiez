package org.radek.restauracja.classes;

import javafx.scene.control.Button;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "zamowienie")
@Table(name = "zamowienie")
public class Zamowienie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "klient_id", nullable = false)
    private Klient klient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "danie_id", nullable = false)
    private Danie danie;

    @Column(nullable = false)
    private String status = "oczekujące";

    @Column(name = "data_zamowienia", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dataZamowienia;

    public Long getId() { return id; }

    public Klient getKlient() { return klient; }

    public void setKlient(Klient klient) { this.klient = klient; }

    public Danie getDanie() { return danie; }

    public void setDanie(Danie danie) { this.danie = danie; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Timestamp getDataZamowienia() { return dataZamowienia; }

    public void setDataZamowienia(Timestamp dataZamowienia) { this.dataZamowienia = dataZamowienia; }
}
