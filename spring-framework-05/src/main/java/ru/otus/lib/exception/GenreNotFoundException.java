package ru.otus.lib.exception;

public class GenreNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2590220474167483468L;

    public GenreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
