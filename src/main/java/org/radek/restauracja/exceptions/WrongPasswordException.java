package org.radek.restauracja.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Błędne hasło");
    }
}
