package ru.otus.lib.exception;

public class BookNotFoundException extends OtusLibraryException {

    private static final long serialVersionUID = 3372692283883673563L;

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(String message) {
        super(message);
    }

}
