package ru.otus.lib.exception;

public class UserAlreadyCommentedBookException extends OtusLibraryException {

    private static final long serialVersionUID = -5254677317705199649L;

    public UserAlreadyCommentedBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyCommentedBookException(String message) {
        super(message);
    }
}
