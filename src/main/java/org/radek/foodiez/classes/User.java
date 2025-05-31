package org.radek.foodiez.classes;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class User extends Osoba{
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String haslo) {
        this.password = haslo;
    }
}
