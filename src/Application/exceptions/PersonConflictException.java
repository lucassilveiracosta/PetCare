package exceptions;

public class PersonConflictException extends RuntimeException {
    public PersonConflictException(String message) {
        super(message);
    }
}
