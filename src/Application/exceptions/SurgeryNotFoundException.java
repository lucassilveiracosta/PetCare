package exceptions;

public class SurgeryNotFoundException extends RuntimeException {
    public SurgeryNotFoundException(String message) {
        super(message);
    }
}
