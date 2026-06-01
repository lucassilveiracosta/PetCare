package exceptions;

public class StockGeneralProductsNotFoundException extends RuntimeException {
    public StockGeneralProductsNotFoundException(String message) {
        super(message);
    }
}
