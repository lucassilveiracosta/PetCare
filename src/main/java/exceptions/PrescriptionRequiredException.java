package exceptions;

/** REQ15 - Raised when a controlled medicine is sold without a linked prescription. */
public class PrescriptionRequiredException extends RuntimeException {
    public PrescriptionRequiredException(String message) {
        super(message);
    }
}
