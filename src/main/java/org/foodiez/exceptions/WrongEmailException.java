package org.foodiez.exceptions;

public class WrongEmailException extends RuntimeException {
    public WrongEmailException() {
        super("zły format adresu email!");
    }
}
