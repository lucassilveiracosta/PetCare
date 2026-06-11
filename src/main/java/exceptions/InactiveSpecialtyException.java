package exceptions;

/** REQ18 - Raised when a surgery is assigned to a veterinarian without an active specialty. */
public class InactiveSpecialtyException extends RuntimeException {
    public InactiveSpecialtyException(String message) {
        super(message);
    }
}
