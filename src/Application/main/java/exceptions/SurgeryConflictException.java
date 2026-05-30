package exceptions;

public class SurgeryConflictException extends RuntimeException {
    public SurgeryConflictException(String message) {
        super(message);
    }
}
