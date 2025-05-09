package org.radek.restauracja.classes;

import jakarta.persistence.*;

@Entity(name = "pracownik")
@Table(name = "pracownik")
public class Pracownik extends Uzytkownik{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rola;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "id=" + id +
                ", rola='" + rola + '\'' +
                '}';
    }
}
