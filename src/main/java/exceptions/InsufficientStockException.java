package exceptions;

/** REQ20 - Raised when a sale would bill more units than there are in stock. */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
