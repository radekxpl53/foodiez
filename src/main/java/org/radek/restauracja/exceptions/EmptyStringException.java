package org.radek.restauracja.exceptions;

public class EmptyStringException extends RuntimeException {
    public EmptyStringException() {
        super("Pusty string!");
    }
}
