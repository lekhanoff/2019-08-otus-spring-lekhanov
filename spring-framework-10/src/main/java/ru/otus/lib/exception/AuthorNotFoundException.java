package ru.otus.lib.exception;

public class AuthorNotFoundException extends OtusLibraryException {

    private static final long serialVersionUID = 2408210704070684240L;

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
