package ru.otus.lib.exception;

public class AuthorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2408210704070684240L;

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
