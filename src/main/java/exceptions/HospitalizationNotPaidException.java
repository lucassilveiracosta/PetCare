package exceptions;

public class HospitalizationNotPaidException extends RuntimeException {
    public HospitalizationNotPaidException(String message) {
        super(message);
    }
}
