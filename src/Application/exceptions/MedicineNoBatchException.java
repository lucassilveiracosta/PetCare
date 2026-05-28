package exceptions;

public class MedicineNoBatchException extends RuntimeException {

    public MedicineNoBatchException(String message) {

        super("400 - Controlled medicines require a valid batch number");
    }
}
