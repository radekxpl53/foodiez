package org.foodiez.exceptions;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(int length) {
        super("hasło musi mieć min. " + length + " znaków!");
    }
}
