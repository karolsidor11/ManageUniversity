package pl.sidor.ManageUniversity.exception;

public class StudentNotFoundException extends Exception {

    public StudentNotFoundException() {
    }
    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
