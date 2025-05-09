package org.radek.restauracja.exceptions;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String fieldName) {
        super("Pole " + fieldName + " jest puste!");
    }
}
