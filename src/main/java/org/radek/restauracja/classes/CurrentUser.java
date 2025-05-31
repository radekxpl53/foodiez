package org.radek.restauracja.classes;

public class CurrentUser {
    private static Klient klient = new Klient();
    private static Pracownik pracownik = new Pracownik();

    public static Klient getKlient() {
        return klient;
    }

    public static void setKlient(Klient klient) {
        CurrentUser.klient = klient;
    }


    public static Pracownik getPracownik() {
        return pracownik;
    }

    public static void setPracownik(Pracownik pracownik) {
        CurrentUser.pracownik = pracownik;
    }
}
