package exceptions;

public class StockVetProductsNotFoundException extends RuntimeException {
    public StockVetProductsNotFoundException(String message) {
        super(message);
    }
}
