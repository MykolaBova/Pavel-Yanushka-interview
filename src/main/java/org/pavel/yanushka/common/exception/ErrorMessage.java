package org.pavel.yanushka.common.exception;
// todo add more
public enum ErrorMessage {
    CUSTOM_MESSAGE("%s");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

