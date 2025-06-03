package org.foodiez.exceptions;

public class WrongPhoneException extends RuntimeException {
    public WrongPhoneException() {
        super("zły numer telefonu!");
    }
}
