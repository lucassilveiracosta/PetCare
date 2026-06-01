package exceptions;

public class StockVetProductsConflictException extends RuntimeException {
    public StockVetProductsConflictException(String message) {
        super(message);
    }
}
