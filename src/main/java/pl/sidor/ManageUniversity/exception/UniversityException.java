package pl.sidor.ManageUniversity.exception;

import lombok.Getter;

@Getter
public class UniversityException extends Exception {

    private final String message;

    public UniversityException(String message) {
        super(message);
        this.message = message;
    }

    public UniversityException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}