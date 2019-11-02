package pl.sidor.ManageUniversity.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class UniversityException extends Exception implements Supplier<Throwable> {

    private final String message;

    public UniversityException(String message) {
        super(message);
        this.message = message;
    }

    public UniversityException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public Throwable get() {
        return  new UniversityException(message);
    }
}