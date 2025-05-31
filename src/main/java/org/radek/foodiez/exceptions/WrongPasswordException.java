package org.radek.foodiez.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Błędne hasło");
    }
}
