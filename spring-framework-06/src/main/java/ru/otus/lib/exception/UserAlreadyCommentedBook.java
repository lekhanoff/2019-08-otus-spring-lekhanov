package ru.otus.lib.exception;

public class UserAlreadyCommentedBook extends OtusLibraryException {

    private static final long serialVersionUID = -5254677317705199649L;

    public UserAlreadyCommentedBook(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyCommentedBook(String message) {
        super(message);
    }
}
