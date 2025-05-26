package org.radek.restauracja.classes;

import jakarta.persistence.*;

import java.util.*;

@Entity(name = "klient")
public class Klient extends Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String adres;

    @OneToMany(mappedBy = "klient", cascade = CascadeType.ALL)
    private List<Zamowienie> zamowienia = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }
}
