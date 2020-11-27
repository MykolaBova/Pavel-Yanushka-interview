package org.pavel.yanushka.common.exception;


public class ApiRequestException extends Exception {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiRequestException(ErrorMessage errorMessage, Object... args) {
        super(String.format(errorMessage.getMessage(), args));
    }
}
