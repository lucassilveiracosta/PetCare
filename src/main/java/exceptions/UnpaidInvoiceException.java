package exceptions;

/** REQ16 - Raised when an animal discharge is attempted with an unpaid invoice. */
public class UnpaidInvoiceException extends RuntimeException {
    public UnpaidInvoiceException(String message) {
        super(message);
    }
}
