package exceptions;

public class RabbiesVaccineExpired extends RuntimeException {
    public RabbiesVaccineExpired(String message) {
        super(message);
    }
}
