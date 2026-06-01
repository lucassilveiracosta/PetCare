package exceptions;

public class InvoiceConflictException extends RuntimeException {
    public InvoiceConflictException(String message) {
        super(message);
    }
}
