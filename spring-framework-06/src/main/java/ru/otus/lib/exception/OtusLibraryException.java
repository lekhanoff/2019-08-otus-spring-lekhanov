package ru.otus.lib.exception;

public class OtusLibraryException extends RuntimeException {

    private static final long serialVersionUID = -1009903067189947331L;

    public OtusLibraryException(String message) {
        super(message);
    }

    public OtusLibraryException(String message, Throwable cause) {
        super(message, cause);
    }

}
