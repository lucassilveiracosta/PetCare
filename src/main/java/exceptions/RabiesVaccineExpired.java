package exceptions;

public class RabiesVaccineExpired extends RuntimeException {
    public RabiesVaccineExpired(String message) {
        super(message);
    }
}
