package exceptions;

public class MedicineInsufficientStockException extends RuntimeException {
    public MedicineInsufficientStockException(String message) {

        super("400 - Insufficient stock for this operation");
    }
}
