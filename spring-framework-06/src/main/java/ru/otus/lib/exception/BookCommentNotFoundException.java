package ru.otus.lib.exception;

public class BookCommentNotFoundException extends OtusLibraryException {

    private static final long serialVersionUID = 2408210704070684240L;

    public BookCommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookCommentNotFoundException(String message) {
        super(message);
    }
}
