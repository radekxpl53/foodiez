package org.radek.restauracja.classes;

public class CurrentKlient {
    private static Klient klient = new Klient();

    public static Klient getKlient() {
        return klient;
    }

    public static void setKlient(Klient klient) {
        CurrentKlient.klient = klient;
    }
}
